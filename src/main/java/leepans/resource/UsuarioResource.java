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
import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.dto.usuario.CadastroCompletoDTO;
import leepans.dto.usuario.CadastroSimplesDTO;
import leepans.dto.usuario.EditarDadosDTO;
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
    @Path("/cadastro/simples")
    public Response createSimples(@Valid CadastroSimplesDTO dto) {
        Usuario usuario = service.create(dto);
        return Response.status(201).entity(usuarioMapper.toResponseDTO(usuario)).build();
    }

    @POST
    @Path("/cadastro/completo")
    public Response createCompleto(@Valid CadastroCompletoDTO dto) {
        Usuario usuario = service.create(dto);
        return Response.status(201).entity(usuarioMapper.toResponseDTO(usuario)).build();
    }

    @GET
    @Path("/admin")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findAll() {
        List<UsuarioResponseDTO> lista = service.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/admin/{id}")
    @RolesAllowed({ "ADMIN", "FUNCIONARIO" })
    public Response findById(@PathParam("id") Long id) {
        UsuarioResponseDTO usuario = usuarioMapper.toResponseDTO(service.findById(id));
        return Response.ok(usuario).build();
    }

    @PATCH
    @Path("/editar/enderecos")
    @RolesAllowed( {"ADMIN", "FUNCIONARIO", "CLIENTE" } )
    public Response setEndereco(EnderecoRequestDTO endereco) {
        String login = jwt.getClaim("upn");
        service.setEndereco(login, endereco);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/editar/senha/{token}")
    @RolesAllowed( { "ADMIN", "FUNCIONARIO", "CLIENTE" } )
    public Response setPassword(@PathParam("token") String token, String novaSenha) {
        String login = jwt.getClaim("upn");
        service.setPassword(login, token, novaSenha);
        return Response.noContent().build();
    }

    @PUT
    @Path("/editar/dados")
    @RolesAllowed( { "ADMIN", "FUNCIONARIO", "CLIENTE" } )
    public Response update (EditarDadosDTO dto){
        String login = jwt.getClaim("upn");
        service.update(login, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
