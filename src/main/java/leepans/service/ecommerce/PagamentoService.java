package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.pagamento.PagamentoPatchDTO;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;
import leepans.repository.PagamentoRepository;

@ApplicationScoped
public class PagamentoService implements PagamentoServiceInter {

    @Inject
    PagamentoRepository repository;

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
    public List<Pagamento> findByTipoPagamento(TipoPagamento tipoPagamento) {
        return repository.findByTipoPagamento(tipoPagamento).list();
    }

    @Override
    public List<Pagamento> findByStatusAndTipo(StatusPagamento statusPagamento, TipoPagamento tipoPagamento) {
        return repository.findByStatusAndTipo(statusPagamento, tipoPagamento).list();
    }

    @Override
    public List<Pagamento> findByValorGreaterThan(Double valor) {
        return repository.findByValorGreaterThan(valor).list();
    }

    @Override
    @Transactional
    public Pagamento create(Pagamento pagamento) {
        repository.persist(pagamento);
        return pagamento;
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
