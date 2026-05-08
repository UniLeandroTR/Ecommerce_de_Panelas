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
import leepans.dto.cor.CorRequestDTO;
import leepans.dto.cor.CorResponseDTO;
import leepans.mapper.CorMapper;
import leepans.model.Cor;
import leepans.service.ecommerce.CorService;

@Path("/cores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CorResource {
    
    @Inject
    CorService service;

    @POST
    @Transactional
    public Response create(@Valid CorRequestDTO dto){
        Cor cor = service.create(CorMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(CorMapper.toResponseDTO(cor)).build();
    }

    @GET
    public Response findAll(){
        List<CorResponseDTO> lista = service.findAll()
        .stream()
        .map(CorMapper::toResponseDTO)
        .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        CorResponseDTO cor = CorMapper.toResponseDTO(service.findById(id));
        if (cor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cor).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome){
        List<CorResponseDTO> lista = service.findByNome(nome)
        .stream()
        .map(CorMapper::toResponseDTO)
        .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CorRequestDTO dto){
        Cor cor = service.findById(id);
        if (cor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
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
