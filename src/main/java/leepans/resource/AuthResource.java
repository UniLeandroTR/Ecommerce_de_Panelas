package leepans.resource;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import leepans.dto.auth.AuthRequestDTO;
import leepans.dto.auth.AuthResponseDTO;
import leepans.dto.auth.ChangePasswordDTO;
import leepans.dto.auth.ForgotPasswordDTO;
import leepans.exception.BadRequestException;
import leepans.service.auth.AuthService;
import leepans.service.auth.EmailService;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @Inject
    EmailService emailService;
    
    @Inject
    JsonWebToken jwt;

    @POST
    @Path("/login")
    public Response login(@Valid AuthRequestDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return Response.ok(response).build();
    }

    @GET
    @Path("info")
    @Authenticated
    public Response info() {
        return Response.ok(authService.info(jwt)).build();
    }

    @POST
    @Path("/alterar-senha")
    @Authenticated
    public Response alterarSenha(@Valid ChangePasswordDTO dto) {
        if (authService.validarRequisicaoSenha(dto)) {
            return Response.ok(emailService.sendPasswordEmail(dto.login())).build();
        }
        throw new BadRequestException("Login ou senha atual incorretos.", "credenciais");
    }

    @POST
    @Path("/esqueci-senha")
    public Response esqueciSenha(@Valid ForgotPasswordDTO dto) {
        return Response.ok(emailService.sendPasswordEmail(dto.login())).build();
    }
}
