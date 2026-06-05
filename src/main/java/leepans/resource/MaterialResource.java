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
import jakarta.ws.rs.core.Response.Status;
import leepans.dto.material.MaterialRequestDTO;
import leepans.dto.material.MaterialResponseDTO;
import leepans.exception.ResourceNotFoundException;
import leepans.exception.ValidationException;
import leepans.mapper.MaterialMapper;
import leepans.model.Material;
import leepans.service.ecommerce.MaterialService;

@Path("/materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {

    @Inject
    MaterialService service;

    @POST
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid MaterialRequestDTO dto) {
        Material material = service.create(MaterialMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(MaterialMapper.toResponseDTO(material)).build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<MaterialResponseDTO> lista = service.findAll()
                .stream()
                .map(MaterialMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        Material material = service.findById(id);
        if (material == null) {
            throw new ResourceNotFoundException("Material", id);
        }
        return Response.ok(MaterialMapper.toResponseDTO(material)).build();
    }

    @GET
    @Path("/admin/nome/{nome}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByNome(@PathParam("nome") String nome) {
        List<MaterialResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(MaterialMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, MaterialRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão do material é obrigatória para atualização.", "version");
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
