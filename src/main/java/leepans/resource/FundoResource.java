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
import leepans.dto.fundo.FundoRequestDTO;
import leepans.dto.fundo.FundoResponseDTO;
import leepans.exception.ValidationException;
import leepans.mapper.FundoMapper;
import leepans.model.Fundo;
import leepans.service.ecommerce.FundoService;

@Path("/fundos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FundoResource {

    @Inject
    FundoService service;

    @Inject
    FundoMapper fundoMapper;

    @POST
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid FundoRequestDTO dto) {
        Fundo fundo = service.create(fundoMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(fundoMapper.toResponseDTO(fundo)).build();
    }

    @GET
    public Response findAll() {
        List<FundoResponseDTO> lista = service.findAll()
                .stream()
                .map(fundoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        FundoResponseDTO fundo = fundoMapper.toResponseDTO(service.findById(id));
        if (fundo == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(fundo).build();
    }

    @GET
    @Path("/cor/{id}")
    public Response findByCor(@PathParam("id") Long idCor) {
        List<FundoResponseDTO> lista = service.findByCor(idCor)
                .stream()
                .map(fundoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, FundoRequestDTO dto) {
        if (dto.version() == null) {
            throw new ValidationException("A versão do fundo é obrigatória para atualização.", "version");
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
