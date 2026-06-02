package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public PanacheQuery<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento) {
        return find("statusPagamento", statusPagamento);
    }

    public PanacheQuery<Pagamento> findByTipoPagamento(TipoPagamento tipoPagamento) {
        return find("tipoPagamento", tipoPagamento);
    }

    public PanacheQuery<Pagamento> findByStatusAndTipo(StatusPagamento statusPagamento, TipoPagamento tipoPagamento) {
        return find("statusPagamento = ?1 and tipoPagamento = ?2", statusPagamento, tipoPagamento);
    }

    public PanacheQuery<Pagamento> findByValorGreaterThan(Double valor) {
        return find("valor > ?1", valor);
    }
}
