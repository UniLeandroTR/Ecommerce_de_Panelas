package leepans.service.ecommerce;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import leepans.exception.ValidationException;
import leepans.model.Boleto;
import leepans.model.CartaoCredito;
import leepans.model.CartaoDebito;
import leepans.model.Endereco;
import leepans.model.ItemPedido;
import leepans.model.Pagamento;
import leepans.model.Pedido;
import leepans.model.Pix;
import leepans.model.StatusPagamento;
import leepans.model.StatusPedido;
import leepans.model.TipoPagamento;
import leepans.model.Usuario;
import leepans.repository.ItemPedidoRepository;
import leepans.repository.PedidoRepository;

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
    PanelaService panelaService;

    @Inject
    PagamentoService pagamentoService;

    @Override
    public List<Pedido> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Pedido findById(Long id) {
        Pedido pedido = repository.findById(id);
        if (pedido == null) {
            throw new ValidationException("Pedido com id " + id + " não encontrado.", "id");
        }
        return pedido;
    }

    @Override
    public List<Pedido> findCompras(String usuarioLogin, StatusPedido status){
        return repository.findComprasUsuario(usuarioLogin, status).list();
    }

    @Override
    public List<Pedido> findCompras(String usuarioLogin){
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
        // Validar e salvar itens do pedido
        Double valorBruto = calcularValorBruto(pedido.getItens());
        Double valorDesconto = 0.0;
        if(pedido.getCupomDesconto() != null){
            valorDesconto = pedido.getCupomDesconto().getValorDesconto();
        }

        Usuario usuario = usuarioService.findByLogin(login);

        Pagamento pagamento = criarPagamento(pedido, tipoPagamento);
        pagamento.setValor(valorBruto - valorDesconto);

        pedido.setUsuario(usuario);
        if(endereco == null)
            pedido.setEndereco(usuario.getEndereco());
        else{
            enderecoService.create(endereco);
            pedido.setEndereco(endereco);
        }
        pedido.setValorBruto(valorBruto);
        pedido.setValorDesconto(valorDesconto);
        pedido.setPagamento(pagamento);

        repository.persist(pedido);
        pagamentoService.create(pagamento);
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
}
