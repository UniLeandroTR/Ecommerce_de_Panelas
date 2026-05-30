package leepans.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.pedido.PedidoRequestDTO;
import leepans.dto.pedido.PedidoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.PedidoMapper;
import leepans.model.Pedido;
import leepans.model.StatusPedido;
import leepans.service.ecommerce.PedidoService;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoService service;

    @Inject
    PedidoMapper pedidoMapper;

    @Inject
    JsonWebToken jwt;

    @GET
    public Response findAll() {
        List<PedidoResponseDTO> lista = service.findAll()
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        PedidoResponseDTO pedido = pedidoMapper.toResponse(service.findById(id));
        return Response.ok(pedido).build();
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response findByUsuarioId(@PathParam("usuarioId") Long usuarioId) {
        List<PedidoResponseDTO> lista = service.findByUsuarioId(usuarioId)
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/status/{status}")
    public Response findByStatus(@PathParam("status") Long status) {
        StatusPedido statusPedido = StatusPedido.valueOf(status);
        if (statusPedido == null) {
            throw new ValidationException("Status com id " + status + " não encontrado.", "status");
        }
        List<PedidoResponseDTO> lista = service.findByStatus(statusPedido)
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/endereco/cidade")
    public Response findByEnderecoCidade(@QueryParam("cidade") String cidade) {
        if (cidade == null || cidade.isBlank()) {
            throw new ValidationException("Cidade é obrigatória para filtrar por endereço.", "cidade");
        }
        List<PedidoResponseDTO> lista = service.findByEnderecoCidade(cidade)
                .stream()
                .map(pedidoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @POST
    @Authenticated
    public Response create(@Valid PedidoRequestDTO dto) {
        Pedido pedido = service.create(pedidoMapper.toEntity(dto), jwt);
        return Response.status(Response.Status.CREATED)
                .entity(pedidoMapper.toResponse(pedido))
                .build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, @Valid PedidoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão do pedido é obrigatória para atualização.", "version");
        }
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
