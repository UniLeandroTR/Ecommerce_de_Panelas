package leepans.service.ecommerce;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.pagamento.BoletoRequestDTO;
import leepans.dto.pagamento.CartaoRequestDTO;
import leepans.dto.pagamento.PagamentoPatchDTO;
import leepans.dto.pagamento.PixRequestDTO;
import leepans.exception.ValidationException;
import leepans.model.Boleto;
import leepans.model.CartaoCredito;
import leepans.model.CartaoDebito;
import leepans.model.Pagamento;
import leepans.model.Pix;
import leepans.model.StatusPagamento;
import leepans.repository.PagamentoRepository;

@ApplicationScoped
public class PagamentoService implements PagamentoServiceInter {

    @Inject
    PagamentoRepository repository;

    @Inject
    CupomDescontoService cupomService;

    @Override
    public List<Pagamento> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Pagamento findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento) {
        return repository.findByStatusPagamento(statusPagamento).list();
    }

    @Override 
    public List<Pagamento> findByUsuario(String login) {
        return repository.findByUsuario(login).list();
    }

    @Override
    @Transactional
    public Pagamento create(Pagamento pagamento) {
        repository.persist(pagamento);
        return pagamento;
    }

    @Override
    @Transactional
    public void processarPagamento(Pagamento pagamento) {
        // Validar se o pagamento existe
        if (pagamento == null || pagamento.getId() == null) {
            throw new ValidationException("Pagamento inválido ou não persistido.", "pagamento");
        }

        // Buscar o pagamento atualizado no banco
        Pagamento pagamentoAtual = repository.findById(pagamento.getId());
        if (pagamentoAtual == null) {
            throw new ValidationException("Pagamento com id " + pagamento.getId() + " não encontrado.", "pagamento");
        }

        // Validar se todos os dados necessários foram preenchidos
        validarDadosPagamento(pagamentoAtual);

        // Atualizar status para APROVADO e registrar data/hora do processamento
        pagamentoAtual.setStatusPagamento(StatusPagamento.APROVADO);
        pagamentoAtual.setDataProcessado(LocalDateTime.now());

        // Persistir as alterações
        repository.persist(pagamentoAtual);
    }

    /**
     * Valida se todos os dados obrigatórios do pagamento foram preenchidos
     * de acordo com seu tipo (Cartão, Boleto, PIX)
     */
    private void validarDadosPagamento(Pagamento pagamento) {
        if (pagamento instanceof CartaoCredito) {
            validarCartao((CartaoCredito) pagamento, "crédito");
        } else if (pagamento instanceof CartaoDebito) {
            validarCartao((CartaoDebito) pagamento, "débito");
        } else if (pagamento instanceof Boleto) {
            validarBoleto((Boleto) pagamento);
        } else if (pagamento instanceof Pix) {
            validarPix((Pix) pagamento);
        } else {
            cupomService.incrementarQuantidade(pagamento.getPedido().getCupomDesconto());
            throw new ValidationException("Tipo de pagamento desconhecido.", "tipoPagamento");
        }
    }

    /**
     * Valida dados do cartão (crédito ou débito)
     */
    private void validarCartao(CartaoCredito cartao, String tipo) {
        if (cartao.getNumero() == null || cartao.getNumero().isBlank()) {
            throw new ValidationException("Número do cartão de " + tipo + " é obrigatório.", "cartao.numero");
        }
        if (cartao.getTitular() == null || cartao.getTitular().isBlank()) {
            throw new ValidationException("Nome do titular do cartão de " + tipo + " é obrigatório.", "cartao.titular");
        }
        if (cartao.getValidade() == null || cartao.getValidade().toString().isBlank()) {
            throw new ValidationException("Validade do cartão de " + tipo + " é obrigatória.", "cartao.validade");
        }
        if (cartao.getCodigoSeguranca() == null || cartao.getCodigoSeguranca().isBlank()) {
            throw new ValidationException("Código de segurança do cartão de " + tipo + " é obrigatório.", "cartao.codigoSeguranca");
        }
    }

    /**
     * Valida dados do cartão débito
     */
    private void validarCartao(CartaoDebito cartao, String tipo) {
        if (cartao.getNumero() == null || cartao.getNumero().isBlank()) {
            throw new ValidationException("Número do cartão de " + tipo + " é obrigatório.", "cartao.numero");
        }
        if (cartao.getTitular() == null || cartao.getTitular().isBlank()) {
            throw new ValidationException("Nome do titular do cartão de " + tipo + " é obrigatório.", "cartao.titular");
        }
        if (cartao.getValidade() == null || cartao.getValidade().toString().isBlank()) {
            throw new ValidationException("Validade do cartão de " + tipo + " é obrigatória.", "cartao.validade");
        }
        if (cartao.getCodigoSeguranca() == null || cartao.getCodigoSeguranca().isBlank()) {
            throw new ValidationException("Código de segurança do cartão de " + tipo + " é obrigatório.", "cartao.codigoSeguranca");
        }
    }

    /**
     * Valida dados do boleto
     */
    private void validarBoleto(Boleto boleto) {
        if (boleto.getCodigoBarras() == null || boleto.getCodigoBarras().isBlank()) {
            throw new ValidationException("Código de barras do boleto é obrigatório.", "boleto.codigoBarras");
        }
    }

    /**
     * Valida dados do PIX
     */
    private void validarPix(Pix pix) {
        if (pix.getChavePix() == null || pix.getChavePix().isBlank()) {
            throw new ValidationException("Chave PIX é obrigatória.", "pix.chavePix");
        }
    }

    @Transactional
    public void completeInfo(Long id, CartaoRequestDTO dto) {
        Pagamento pagamento = repository.findById(id);

        if (dto.isCredito()) {
            if (!(pagamento instanceof CartaoCredito)) {
                throw new IllegalStateException(
                        "O pagamento não é um cartão de crédito.");
            }
            CartaoCredito cartaoCredito = (CartaoCredito) pagamento;
            cartaoCredito.setNumero(dto.numero());
            cartaoCredito.setTitular(dto.titular());
            cartaoCredito.setValidade(dto.validade());
            cartaoCredito.setCodigoSeguranca(dto.codigoSeguranca());
        } else {
            if (!(pagamento instanceof CartaoDebito)) {
                throw new IllegalStateException(
                        "O pagamento não é um cartão de débito.");
            }
            CartaoDebito cartaoDebito = (CartaoDebito) pagamento;
            cartaoDebito.setNumero(dto.numero());
            cartaoDebito.setTitular(dto.titular());
            cartaoDebito.setValidade(dto.validade());
            cartaoDebito.setCodigoSeguranca(dto.codigoSeguranca());
        }

        repository.persist(pagamento);
    }

    @Override
    @Transactional
    public void completeInfo(Long id, BoletoRequestDTO dto) {
        Pagamento pagamento = repository.findById(id);

        if (!(pagamento instanceof Boleto)) {
            throw new IllegalStateException(
                    "O pagamento não é um boleto.");
        }

        Boleto boleto = (Boleto) pagamento;
        boleto.setCodigoBarras(dto.codigoBarras());

        repository.persist(pagamento);
    }

    @Override
    @Transactional
    public void completeInfo(Long id, PixRequestDTO dto) {
        Pagamento pagamento = repository.findById(id);

        if (!(pagamento instanceof Pix)) {
            throw new IllegalStateException(
                    "O pagamento não é um pix.");
        }

        Pix pix = (Pix) pagamento;
        pix.setChavePix(dto.chavePix());

        repository.persist(pagamento);
    }

    @Override
    @Transactional
    public void setStatus(Long id, PagamentoPatchDTO dto) {
        Pagamento pagamento = repository.findById(id);

        if (pagamento.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                    "Conflito de concorrência: o pagamento foi alterado por outra transação."
            );
        }

        pagamento.setStatusPagamento(dto.statusPagamento());
        repository.persist(pagamento);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
