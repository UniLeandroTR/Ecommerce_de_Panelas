package leepans.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.dto.listaDesejo.ListaDesejoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.ListaDesejoMapper;
import leepans.model.ListaDesejo;
import leepans.service.ecommerce.ListaDesejoService;

@Path("/listas-desejo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ListaDesejoResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    ListaDesejoService service;

    @Inject
    ListaDesejoMapper listaDesejoMapper;

    @POST
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response create(List<Long> idProdutos) {
        ListaDesejo created = service.create(idProdutos, jwt);
        return Response.status(Response.Status.CREATED).entity(listaDesejoMapper.toResponse(created)).build();
    }

    @GET
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findWishList() {
        ListaDesejoResponseDTO lista = listaDesejoMapper.toResponse(service.findWishList(jwt));
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        ListaDesejoResponseDTO listaDesejo = listaDesejoMapper.toResponse(service.findById(id));
        return Response.ok(listaDesejo).build();
    }

    @GET
    @Path("/admin/usuario/{usuarioId}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByUsuarioLogin(@PathParam("usuarioLogin") String usuarioLogin) {
        ListaDesejoResponseDTO listaDesejo = listaDesejoMapper.toResponse(service.findByUsuarioLogin(usuarioLogin));
        return Response.ok(listaDesejo).build();
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, @Valid ListaDesejoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão da lista de desejo é obrigatória para atualização.", "version");
        }
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{listaDesejoId}/produtos/{panelaId}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response adicionarProduto(
        @PathParam("listaDesejoId") Long listaDesejoId,
        @PathParam("panelaId") Long panelaId
    ) {
        service.adicionarProduto(listaDesejoId, panelaId);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{listaDesejoId}/produtos/{panelaId}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response removerProduto(
        @PathParam("listaDesejoId") Long listaDesejoId,
        @PathParam("panelaId") Long panelaId
    ) {
        service.removerProduto(listaDesejoId, panelaId);
        return Response.noContent().build();
    }
}
