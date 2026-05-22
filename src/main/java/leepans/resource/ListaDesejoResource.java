package leepans.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.listaDesejo.ListaDesejoRequestDTO;
import leepans.dto.listaDesejo.ListaDesejoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.ListaDesejoMapper;
import leepans.model.ListaDesejo;
import leepans.service.ecommerce.ListaDesejoService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

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
    @Transactional
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response create(@Valid ListaDesejoRequestDTO dto) {
        ListaDesejo listaDesejo = listaDesejoMapper.toEntity(dto);
        ListaDesejo created = service.create(listaDesejo);
        return Response.status(Response.Status.CREATED).entity(listaDesejoMapper.toResponse(created)).build();
    }

    @GET
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findWishList() {
        ListaDesejoResponseDTO lista = listaDesejoMapper.toResponse(service.findWishList(jwt));
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findById(@PathParam("id") Long id) {
        ListaDesejoResponseDTO listaDesejo = listaDesejoMapper.toResponse(service.findById(id));
        return Response.ok(listaDesejo).build();
    }

    @GET
    @Path("/usuario/{usuarioId}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findByUsuarioId(@PathParam("usuarioId") Long usuarioId) {
        ListaDesejoResponseDTO listaDesejo = listaDesejoMapper.toResponse(service.findByUsuarioId(usuarioId));
        return Response.ok(listaDesejo).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response update(@PathParam("id") Long id, @Valid ListaDesejoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão da lista de desejo é obrigatória para atualização.", "version");
        }
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "CLIENTE" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Transactional
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
    @Transactional
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
