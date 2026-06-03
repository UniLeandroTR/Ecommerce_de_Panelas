package leepans.service.auth;

import org.eclipse.microprofile.jwt.JsonWebToken;

import leepans.dto.auth.AuthRequestDTO;
import leepans.dto.auth.AuthResponseDTO;
import leepans.dto.auth.ForgotPasswordDTO;
import leepans.dto.usuario.UsuarioResponseDTO;

public interface AuthServiceInter {

    /**
     * Autentica o usuario e retorna um token JWT em caso de sucesso.
     */
    AuthResponseDTO login(AuthRequestDTO dto);

    UsuarioResponseDTO info(JsonWebToken jwt);

    String alterarSenha(ForgotPasswordDTO dto);
}
