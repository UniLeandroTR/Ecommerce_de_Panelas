package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Pedido;
import leepans.model.StatusPedido;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public PanacheQuery<Pedido> findByUsuarioId(Long usuarioId) {
        return find("SELECT p FROM Pedido p WHERE p.usuario.id = ?1", usuarioId);
    }

    public PanacheQuery<Pedido> findByStatus(StatusPedido status) {
        return find("SELECT p FROM Pedido p WHERE p.status = ?1", status);
    }

    public PanacheQuery<Pedido> findByEnderecoCidade(String cidade) {
        return find("SELECT p FROM Pedido p WHERE UPPER(p.endereco.cidade) LIKE UPPER(?1)", "%" + cidade + "%");
    }
}
