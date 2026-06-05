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
import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.dto.endereco.EnderecoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.EnderecoMapper;
import leepans.model.Endereco;
import leepans.service.ecommerce.EnderecoService;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<EnderecoResponseDTO> lista = service.findAll()
                .stream()
                .map(EnderecoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        EnderecoResponseDTO endereco = EnderecoMapper.toResponse(service.findById(id));
        return Response.ok(endereco).build();
    }
    
    @GET
    @Path("/usuario")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" , "CLIENTE" })
    public Response findByUsuario() {
        String login = jwt.getClaim("upn");
        EnderecoResponseDTO endereco = EnderecoMapper.toResponse(service.findByUsuario(login));
        return Response.ok(endereco).build();
    }


    @GET
    @Path("/admin/cidade/{cidade}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByCidade(@PathParam("cidade") String cidade) {
        List<EnderecoResponseDTO> lista = service.findByCidade(cidade)
                .stream()
                .map(EnderecoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/estado/{estado}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findByEstado(@PathParam("estado") String estado) {
        List<EnderecoResponseDTO> lista = service.findByEstado(estado)
                .stream()
                .map(EnderecoMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @POST
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid EnderecoRequestDTO dto) {
        Endereco endereco = service.create(EnderecoMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED)
                .entity(EnderecoMapper.toResponse(endereco))
                .build();
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, @Valid EnderecoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão do endereço é obrigatória para atualização.", "version");
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
