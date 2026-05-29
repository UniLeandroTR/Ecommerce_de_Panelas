package leepans.resource;

import java.util.List;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.usuario.UsuarioRequestDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.mapper.UsuarioMapper;
import leepans.model.Usuario;
import leepans.service.ecommerce.UsuarioService;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioMapper usuarioMapper;

    @POST
    @RolesAllowed( {"ADMIN", "FUNCIONARIO" })
    public Response create(@Valid UsuarioRequestDTO dto){
        Usuario usuario = service.create(usuarioMapper.toEntity(dto));
        return Response.status(201).entity(usuarioMapper.toResponseDTO(usuario)).build();
    }

    @GET
    public Response findAll(){
        List<UsuarioResponseDTO> lista = service.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed( {"ADMIN", "FUNCIONARIO" } )
    public Response findById(@PathParam("id") Long id){
        UsuarioResponseDTO usuario = usuarioMapper.toResponseDTO(service.findById(id));
        return Response.ok(usuario).build();
    }

    @PATCH
    @Path("/enderecos/{id}")
    @RolesAllowed( {"ADMIN", "FUNCIONARIO", "CLIENTE" } )
    public Response setEndereco(@PathParam("id") Long enderecoId) {
        String login = jwt.getClaim("upn");
        service.setEndereco(login, enderecoId);
        return Response.noContent().build();
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
