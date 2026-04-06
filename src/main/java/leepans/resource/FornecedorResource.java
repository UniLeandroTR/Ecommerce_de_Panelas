package leepans.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.FornecedorRequestDTO;
import leepans.dto.FornecedorResponseDTO;
import leepans.mapper.FornecedorMapper;
import leepans.model.Fornecedor;
import leepans.service.FornecedorService;

import java.util.List;

@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    @Inject
    FornecedorService service;

    @POST
    @Transactional
    public Response create(FornecedorRequestDTO dto){
        Fornecedor fornecedor = service.create(FornecedorMapper.toEntity(dto));
        return Response.status(Response.Status.CREATED).entity(FornecedorMapper.toResponse(fornecedor)).build();
    }

    @GET
    public Response findAll(){
        List<FornecedorResponseDTO> lista = service.findAll()
                .stream()
                .map(FornecedorMapper::toResponse)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id){
        FornecedorResponseDTO fornecedor = FornecedorMapper.toResponse(service.findById(id));
        return Response.ok(fornecedor).build();
    }


    @PUT
    @Transactional
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FornecedorRequestDTO dto){
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
