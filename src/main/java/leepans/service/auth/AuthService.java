package leepans.service.auth;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import leepans.dto.auth.AuthRequestDTO;
import leepans.dto.auth.AuthResponseDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.mapper.EnderecoMapper;
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
        try {
            Usuario usuario = usuarioRepository.findByLogin(dto.login()).singleResult();
            // Verifica se a senha fornecida bate com o hash armazenado
            if (!hashService.verificarBcrypt(dto.senha(), usuario.getSenhaHash())) {
                throw new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED);
            }

            String token = jwtService.gerarToken(usuario);
            return new AuthResponseDTO(token, "Bearer");
        } catch (NoResultException e) {
            throw new WebApplicationException("Login ou senha invalidos", Status.UNAUTHORIZED);
        }
    }

    @Override
    public UsuarioResponseDTO info(JsonWebToken jwt) {
        String login = (String) jwt.getClaim("upn");
        Usuario usuario = usuarioRepository.findByLogin(login).singleResult();
        return new UsuarioResponseDTO(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getPerfil(), EnderecoMapper.toResponse(usuario.getEndereco()));
    }
}
