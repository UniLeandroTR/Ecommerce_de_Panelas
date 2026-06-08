package leepans.resource;

import java.util.List;

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
import leepans.dto.panela.PanelaEcommerceDTO;
import leepans.dto.panela.PanelaRequestDTO;
import leepans.dto.panela.PanelaResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.PanelaMapper;
import leepans.model.Panela;
import leepans.service.ecommerce.PanelaService;

@Path("/panelas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PanelaResource {

    @Inject
    PanelaService service;

    @Inject
    PanelaMapper mapper;

    @POST
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid PanelaRequestDTO dto) {
        Panela panela = service.create(mapper.toEntity(dto));
        return Response.status(201).entity(mapper.toResponseDTO(panela)).build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<PanelaResponseDTO> lista = service.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        return Response.ok(mapper.toResponseDTO(service.findById(id))).build();
    }

    @GET
    @Path("/categorias/{id}")
    public Response findByCategoria(@PathParam("id") Long id) {
        List<PanelaEcommerceDTO> lista = service.findByCategoria(id)
                .stream()
                .map(mapper::toEcommerceDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/colecoes/{id}")
    public Response findByColecao(@PathParam("id") Long id) {
        List<PanelaEcommerceDTO> lista = service.findByColecao(id)
                .stream()
                .map(mapper::toEcommerceDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    public Response findAllEcommerce() {
        List<PanelaEcommerceDTO> lista = service.findAll()
                .stream()
                .map(mapper::toEcommerceDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO", "CLIENTE" })
    public Response findByIdEcommerce(@PathParam("id") Long id) {
        return Response.ok(mapper.toEcommerceDTO(service.findById(id))).build();
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, PanelaRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão da panela é obrigatória para atualização.", "version");
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
}
