package leepans.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.categoria.CategoriaRequestDTO;
import leepans.dto.categoria.CategoriaResponseDTO;
import leepans.mapper.CategoriaMapper;
import leepans.model.Categoria;
import leepans.service.ecommerce.CategoriaService;

import java.util.List;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {

    @Inject
    CategoriaService service;

    @POST
    @Transactional
    public Response create(@Valid CategoriaRequestDTO dto){
        Categoria categoria = service.create(CategoriaMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(CategoriaMapper.toResponse(categoria)).build();
    }

    @GET
    public Response findAll(){
        List<CategoriaResponseDTO> lista = service.findAll()
                .stream()
                .map(CategoriaMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome){
        List<CategoriaResponseDTO> lista = service.findByNome(nome)
            .stream()
            .map(CategoriaMapper::toResponse)
            .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        CategoriaResponseDTO categoria = CategoriaMapper.toResponse(service.findById(id));
        return Response.ok(categoria).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CategoriaRequestDTO dto){
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
