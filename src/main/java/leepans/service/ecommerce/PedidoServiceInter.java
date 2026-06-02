package leepans.service.ecommerce;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import leepans.model.Pedido;
import leepans.model.StatusPedido;

public interface PedidoServiceInter {

    List<Pedido> findAll();

    Pedido findById(Long id);

    List<Pedido> findCompras(String usuarioLogin);

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findByStatus(StatusPedido status);

    List<Pedido> findByEnderecoCidade(String cidade);

    Pedido create(Pedido pedido, JsonWebToken jwt);

    void setStatus(Long id, StatusPedido status);

    void delete(Long id);
}
