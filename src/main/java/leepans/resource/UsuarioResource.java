package leepans.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.mapper.UsuarioMapper;
import leepans.model.Usuario;
import leepans.service.ecommerce.UsuarioService;

import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @POST
    @RolesAllowed( {"ADMIN", "FUNCIONARIO" })
    public Response create(@Valid UsuarioRequestDTO dto){
        Usuario usuario = service.create(UsuarioMapper.toEntity(dto));
        return Response.status(201).entity(UsuarioMapper.toResponseDTO(usuario)).build();
    }

    @GET
    public Response findAll(){
        List<UsuarioResponseDTO> lista = service.findAll()
                .stream()
                .map(UsuarioMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed( {"ADMIN", "FUNCIONARIO" } )
    public Response findById(@PathParam("id") Long id){
        UsuarioResponseDTO usuario = UsuarioMapper.toResponseDTO(service.findById(id));
        return Response.ok(usuario).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed( { "ADMIN" } )
    public Response update (@PathParam("id") Long id, UsuarioRequestDTO dto){
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed( "ADMIN" )
    public Response delete (@PathParam("id") Long id){
        service.delete(id);
        return Response.noContent().build();
    }
}
