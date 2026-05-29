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
import leepans.dto.fornecedor.FornecedorRequestDTO;
import leepans.dto.fornecedor.FornecedorResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.FornecedorMapper;
import leepans.model.Fornecedor;
import leepans.service.ecommerce.FornecedorService;

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    @Inject
    FornecedorService service;

    @POST
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid FornecedorRequestDTO dto) {
        Fornecedor fornecedor = service.create(FornecedorMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(FornecedorMapper.toResponse(fornecedor)).build();
    }

    @GET
    public Response findAll() {
        List<FornecedorResponseDTO> lista = service.findAll()
                .stream()
                .map(FornecedorMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        FornecedorResponseDTO fornecedor = FornecedorMapper.toResponse(service.findById(id));
        return Response.ok(fornecedor).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<FornecedorResponseDTO> lista = service.findByNome(nome)
                .stream()
                .map(FornecedorMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, FornecedorRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão do fornecedor é obrigatória para atualização.", "version");
        }
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
