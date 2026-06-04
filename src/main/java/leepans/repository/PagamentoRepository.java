package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;

@ApplicationScoped
public class PagamentoRepository implements PanacheRepository<Pagamento> {

    public PanacheQuery<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento) {
        return find("statusPagamento", statusPagamento);
    }

    public PanacheQuery<Pagamento> findByUsuario(String login) {
        return find("SELECT p FROM Pagamento p " +
                "WHERE p.pedido.usuario.login = ?1", login);
    }
}
