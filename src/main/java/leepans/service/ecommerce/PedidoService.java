package leepans.service.ecommerce;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import leepans.exception.ResourceNotFoundException;
import leepans.exception.ValidationException;
import leepans.model.Boleto;
import leepans.model.CartaoCredito;
import leepans.model.CartaoDebito;
import leepans.model.CupomDesconto;
import leepans.model.Endereco;
import leepans.model.ItemPedido;
import leepans.model.Pagamento;
import leepans.model.Pedido;
import leepans.model.Pix;
import leepans.model.StatusPagamento;
import leepans.model.StatusPedido;
import leepans.model.TipoPagamento;
import leepans.model.Usuario;
import leepans.repository.EnderecoRepository;
import leepans.repository.ItemPedidoRepository;
import leepans.repository.PedidoRepository;
import leepans.service.auth.EmailService;

@ApplicationScoped
public class PedidoService implements PedidoServiceInter {

    @Inject
    PedidoRepository repository;

    @Inject
    ItemPedidoRepository itemRepository;

    @Inject
    UsuarioService usuarioService;

    @Inject
    EnderecoService enderecoService;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    PanelaService panelaService;

    @Inject
    PagamentoService pagamentoService;

    @Inject
    CupomDescontoService cupomDescontoService;

    @Inject
    EmailService emailService;

    @Override
    public List<Pedido> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Pedido findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new ResourceNotFoundException("Pedido", id);
        }
        return pedido;
    }

    @Override
    public List<Pedido> findCompras(String usuarioLogin, StatusPedido status) {
        return repository.findComprasUsuario(usuarioLogin, status).list();
    }

    @Override
    public List<Pedido> findCompras(String usuarioLogin) {
        return repository.findComprasUsuario(usuarioLogin).list();
    }

    @Override
    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).list();
    }

    @Override
    public List<Pedido> findByStatus(StatusPedido status) {
        return repository.findByStatus(status).list();
    }

    @Override
    public List<Pedido> findByEnderecoCidade(String cidade) {
        return repository.findByEnderecoCidade(cidade).list();
    }

    @Override
    @Transactional
    public Pedido create(Pedido pedido, String login, Endereco endereco, TipoPagamento tipoPagamento) {
        // Validar itens do pedido
        validarItens(pedido.getItens());

        // Calcular valor bruto
        Double valorBruto = calcularValorBruto(pedido.getItens());

        // Validar e aplicar cupom desconto
        Double valorDesconto = validarEAplicarCupomDesconto(pedido.getCupomDesconto(), valorBruto);

        // Buscar o usuário
        Usuario usuario = usuarioService.findByLogin(login);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário", login);
        }

        // Validar e processar endereço
        Endereco enderecoFinal = validarEProcessarEndereco(endereco, usuario);

        // Criar e configurar pagamento
        Pagamento pagamento = criarPagamento(pedido, tipoPagamento);
        pagamento.setValor(valorBruto - valorDesconto);

        // Configurar pedido
        pedido.setUsuario(usuario);
        pedido.setEndereco(enderecoFinal);
        pedido.setValorBruto(valorBruto);
        pedido.setValorDesconto(valorDesconto);
        pedido.setPagamento(pagamento);
        pedido.setStatus(StatusPedido.PENDENTE);

        // Persistir pedido e pagamento
        repository.persist(pedido);
        pagamentoService.create(pagamento);

        //Enviar email de confirmação para o cliente
        emailService.sendOrderConfirmedEmail(pedido.getUsuario().getNome(), pedido.getId().toString());


        return pedido;
    }

    @Override
    @Transactional
    public void setStatus(Long id, StatusPedido status) {
        Pedido pedido = findById(id);
        pedido.setStatus(status);
        repository.persist(pedido);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Pedido pedido = findById(id);

        // Deletar itens do pedido
        if (pedido.getItens() != null) {
            for (ItemPedido item : pedido.getItens()) {
                itemRepository.deleteById(item.getId());
            }
        }

        repository.deleteById(id);
    }

    private Pagamento criarPagamento(Pedido pedido, TipoPagamento tipoPagamento) {
        Pagamento pagamento = null;
        if (tipoPagamento == TipoPagamento.CARTAO_CREDITO)
            pagamento = new CartaoCredito();
        else if (tipoPagamento == TipoPagamento.CARTAO_DEBITO)
            pagamento = new CartaoDebito();
        else if (tipoPagamento == TipoPagamento.BOLETO)
            pagamento = new Boleto();
        else
            pagamento = new Pix();
        pagamento.setPedido(pedido);
        pagamento.setTipoPagamento(tipoPagamento);
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setDataProcessado(LocalDateTime.now());
        return pagamento;
    }

    private double calcularValorBruto(List<ItemPedido> itens) {
        double valorBruto = 0.0;
        for (ItemPedido item : itens) {
            valorBruto += item.getValorUnitario() * item.getQuantidade();
        }
        return valorBruto;
    }

    /**
     * Valida os itens do pedido
     */
    private void validarItens(List<ItemPedido> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new ValidationException("O pedido deve conter pelo menos um item.", "itens");
        }

        for (ItemPedido item : itens) {
            if (item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new ValidationException("A quantidade de cada item deve ser maior que zero.", "quantidade");
            }
            if (item.getValorUnitario() == null || item.getValorUnitario() < 0) {
                throw new ValidationException("O valor unitário não pode ser negativo.", "valorUnitario");
            }
        }
    }

    /**
     * Valida e aplica o cupom desconto ao pedido
     */
    private Double validarEAplicarCupomDesconto(CupomDesconto cupomDesconto, Double valorBruto) {
        if (cupomDesconto == null) {
            return 0.0;
        }

        // Validar se o cupom está ativo
        if (!cupomDesconto.isAtivo()) {
            throw new ValidationException("O cupom desconto está inativo.", "cupomDesconto");
        }

        // Validar se o cupom não expirou
        if (cupomDesconto.getDataValidade() != null && cupomDesconto.getDataValidade().isBefore(LocalDateTime.now())) {
            throw new ValidationException("O cupom desconto expirou.", "cupomDesconto");
        }

        // Validar se tem quantidade disponível
        if (cupomDesconto.getQuantidadeDisponivel() != null && cupomDesconto.getQuantidadeDisponivel() <= 0) {
            throw new ValidationException("O cupom desconto não possui quantidade disponível.", "cupomDesconto");
        }

        // Validar valor mínimo de compra
        if (cupomDesconto.getValorMinimoCompra() != null && cupomDesconto.getValorMinimoCompra() > valorBruto) {
            throw new ValidationException(
                    "O valor mínimo de compra para este cupom é R$ " + cupomDesconto.getValorMinimoCompra(),
                    "cupomDesconto");
        }

        // Retornar valor do desconto
        if (cupomDesconto.getValorDesconto() != null && cupomDesconto.getValorDesconto() > 0) {
            return cupomDesconto.getValorDesconto();
        } else if (cupomDesconto.getPercentualDesconto() != null && cupomDesconto.getPercentualDesconto() > 0) {
            return valorBruto * (cupomDesconto.getPercentualDesconto() / 100.0);
        }

        // Decrementar quantidade disponível do cupom para reservar ao cliente
        cupomDescontoService.decrementarQuantidade(cupomDesconto);

        return 0.0;
    }

    /**
     * Valida e processa o endereço do pedido
     */
    private Endereco validarEProcessarEndereco(Endereco endereco, Usuario usuario) {
        // Se não foi fornecido endereço, usar o endereço do usuário
        if (endereco == null) {
            if (usuario.getEndereco() == null) {
                throw new ValidationException(
                        "Endereço não fornecido e usuário não possui endereço registrado.",
                        "endereco");
            }
            return usuario.getEndereco();
        }

        // Validar campos obrigatórios do endereço
        if (endereco.getRua() == null || endereco.getRua().isBlank()) {
            throw new ValidationException("A rua do endereço é obrigatória.", "endereco.rua");
        }
        if (endereco.getNumero() == null || endereco.getNumero().isBlank()) {
            throw new ValidationException("O número do endereço é obrigatório.", "endereco.numero");
        }
        if (endereco.getCidade() == null || endereco.getCidade().isBlank()) {
            throw new ValidationException("A cidade do endereço é obrigatória.", "endereco.cidade");
        }
        if (endereco.getEstado() == null || endereco.getEstado().isBlank()) {
            throw new ValidationException("O estado do endereço é obrigatório.", "endereco.estado");
        }
        if (endereco.getCep() == null || endereco.getCep().isBlank()) {
            throw new ValidationException("O CEP do endereço é obrigatório.", "endereco.cep");
        }

        // Verificar se o endereço já existe no banco de dados
        Endereco enderecoExistente = enderecoRepository.findByAllFields(
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getCep());

        if (enderecoExistente != null) {
            // Se o endereço já existe, usar o existente
            return enderecoExistente;
        }

        // Se não existe, criar um novo endereço
        enderecoService.create(endereco);
        return endereco;
    }
}
