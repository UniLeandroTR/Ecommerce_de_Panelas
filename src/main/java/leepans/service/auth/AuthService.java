package leepans.service.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import leepans.dto.auth.AuthRequestDTO;
import leepans.dto.auth.AuthResponseDTO;
import leepans.model.Usuario;
import leepans.repository.UsuarioRepository;

@ApplicationScoped
public class AuthService implements AuthServiceInter {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        // Busca o usuario pelo login; retorna 401 se nao encontrado
        Usuario usuario = usuarioRepository.findByLogin(dto.login())
                .orElseThrow(() -> new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED));

        // Verifica se a senha fornecida bate com o hash armazenado
        if (!hashService.verificarBcrypt(dto.senha(), usuario.getSenhaHash())) {
            throw new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED);
        }

        String token = jwtService.gerarToken(usuario);
        return new AuthResponseDTO(token, "Bearer");
    }
}
