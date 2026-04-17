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
import leepans.dto.panela.PanelaRequestDTO;
import leepans.dto.panela.PanelaResponseDTO;
import leepans.mapper.PanelaMapper;
import leepans.model.Panela;
import leepans.service.PanelaService;

@Path("/panelas")
@Produces(MediaType.APPLICATION_JSON )
@Consumes(MediaType.APPLICATION_JSON )
public class PanelaResource {
    
    @Inject
    PanelaService service;

    @Inject
    PanelaMapper mapper;

    @POST
    @Transactional
    public Response create (@Valid PanelaRequestDTO dto){
        Panela panela = service.create(mapper.toEntity(dto));
        return Response.status(201).entity(mapper.toResponseDTO(panela)).build();
    }

    @GET
    public Response findAll(){
        List<PanelaResponseDTO> lista = service.findAll()
            .stream()
            .map(mapper::toResponseDTO)
            .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        return Response.ok(mapper.toResponseDTO(service.findById(id))).build();
    }

    @GET
    @Path("/categoria/{id}")
    public Response findByCategoria(@PathParam("id") Long id){
        List<PanelaResponseDTO> lista = service.findByCategoria(id)
        .stream()
        .map(mapper::toResponseDTO)
        .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/colecao/{id}")
    public Response findByColecao(@PathParam("id") Long id){
        List<PanelaResponseDTO> lista = service.findByColecao(id)
        .stream()
        .map(mapper::toResponseDTO)
        .toList();
        return Response.ok(lista).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, PanelaRequestDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
