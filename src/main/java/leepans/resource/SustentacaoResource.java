package leepans.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import leepans.dto.sustentacao.SustentacaoRequestDTO;
import leepans.dto.sustentacao.SustentacaoResponseDTO;
import leepans.mapper.SustentacaoMapper;
import leepans.model.Sustentacao;
import leepans.service.ecommerce.SustentacaoService;

@Path("/sustentacoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SustentacaoResource {

    @Inject
    SustentacaoService service;

    @Inject
    SustentacaoMapper sustentacaoMapper;

    @POST
    @Transactional
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response create(@Valid SustentacaoRequestDTO dto) {
        Sustentacao sustentacao = service.create(sustentacaoMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(sustentacaoMapper.toResponseDTO(sustentacao)).build();
    }

    @GET
    public Response findAll() {
        List<SustentacaoResponseDTO> lista = service.findAll()
                .stream()
                .map(sustentacaoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        SustentacaoResponseDTO sustentacao = sustentacaoMapper.toResponseDTO(service.findById(id));
        if (sustentacao == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(sustentacao).build();
    }

    @GET
    @Path("/material/{id}")
    public Response findByMaterial(@PathParam("id") Long id) {
        List<SustentacaoResponseDTO> lista = service.findByMaterial(id)
                .stream()
                .map(sustentacaoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response update(@PathParam("id") Long id, SustentacaoRequestDTO dto) {
        service.update(id, dto);
        Sustentacao sustentacao = service.findById(id);
        if (sustentacao == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    @RolesAllowed({ "ADMIN" })
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
