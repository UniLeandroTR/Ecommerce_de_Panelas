package leepans.resource;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import leepans.dto.MaterialRequestDTO;
import leepans.dto.MaterialResponseDTO;
import leepans.mapper.MaterialMapper;
import leepans.model.Material;
import leepans.service.MaterialService;

@Path("/materiais")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {
    
    @Inject
    MaterialService service;

    @POST
    @Transactional
    public Response create(MaterialRequestDTO dto){
        Material material = service.create(MaterialMapper.toEntity(dto));
        return Response.status(Status.CREATED).entity(MaterialMapper.toResponseDTO(material)).build();
    }

    @GET
    public Response findAll(){
        List<MaterialResponseDTO> lista = service.findAll()
            .stream()
            .map(MaterialMapper::toResponseDTO)
            .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        MaterialResponseDTO material = MaterialMapper.toResponseDTO(service.findById(id));
        if (material == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(material).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, MaterialRequestDTO dto){
        Material material = service.findById(id);
        if (material == null) {
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
