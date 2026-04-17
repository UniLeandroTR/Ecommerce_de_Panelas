package leepans.resource;

import java.util.List;

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
import leepans.dto.tampa.TampaRequestDTO;
import leepans.dto.tampa.TampaResponseDTO;
import leepans.mapper.TampaMapper;
import leepans.model.Tampa;
import leepans.service.TampaService;

@Path("/tampas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TampaResource {
    
    @Inject
    TampaService service;

    @Inject
    TampaMapper tampaMapper;

    @POST
    @Transactional
    public Response create(@Valid TampaRequestDTO dto){
        Tampa tampa = service.create(tampaMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(tampaMapper.toResponseDTO(tampa)).build();
    }

    @GET
    public Response findAll(){
        List<TampaResponseDTO> lista = service.findAll()
            .stream()
            .map(tampaMapper::toResponseDTO)
            .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        TampaResponseDTO tampa = tampaMapper.toResponseDTO(service.findById(id));
        if (tampa == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(tampa).build();
    }

    @GET
    @Path("/material/{id}")
    public Response findByMaterial(@PathParam("id") Long id){
        List<TampaResponseDTO> lista = service.findByMaterial(id)
        .stream()
        .map(tampaMapper::toResponseDTO)
        .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TampaRequestDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
