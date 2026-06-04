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
import leepans.dto.categoria.CategoriaRequestDTO;
import leepans.dto.categoria.CategoriaResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.CategoriaMapper;
import leepans.model.Categoria;
import leepans.service.ecommerce.CategoriaService;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    @POST
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid CategoriaRequestDTO dto) {
        Categoria categoria = service.create(CategoriaMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(CategoriaMapper.toResponse(categoria)).build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<CategoriaResponseDTO> lista = service.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    @Path("/admin/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<CategoriaResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    @Path("/admin/{id}")
    public Response findById(@PathParam("id") Long id) {
        CategoriaResponseDTO categoria = CategoriaMapper.toResponse(service.findById(id));
        return Response.ok(categoria).build();
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, CategoriaRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão da categoria é obrigatória para atualização.", "version");
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
