package leepans.service.auth;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import leepans.dto.auth.AuthRequestDTO;
import leepans.dto.auth.AuthResponseDTO;
import leepans.dto.auth.ForgotPasswordDTO;
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

    @Inject
    CacheService cacheService;

    @Override
    public AuthResponseDTO login(AuthRequestDTO dto) {
        try {
            Usuario usuario = usuarioRepository.findByLogin(dto.login()).singleResult();
            // Verifica se a senha fornecida bate com o hash armazenado
            if (usuario == null || !hashService.verifyArgon2(dto.senha(), usuario.getSenhaHash())) {
                throw new NotAuthorizedException("Credenciais inválidas");
            }

            String token = jwtService.gerarToken(usuario);
            return new AuthResponseDTO(token, "Bearer");
        } catch (NoResultException e) {
            throw new NotAuthorizedException("Login ou senha inválidos");
        }
    }

    @Override
    public UsuarioResponseDTO info(JsonWebToken jwt) {
        String login = (String) jwt.getClaim("upn");
        Usuario usuario = usuarioRepository.findByLogin(login).singleResult();
        return new UsuarioResponseDTO(usuario.getId(), usuario.getLogin(), usuario.getNome(), usuario.getPerfil(),
                EnderecoMapper.toResponse(usuario.getEndereco()));
    }

    @Override
    @Transactional
    public String alterarSenha(ForgotPasswordDTO dto) {
        if (!validarRequisicaoSenha(dto)) {
            throw new NotAuthorizedException("Login ou Senha atual incorretos.");
        }

        String token = cacheService.getTokenSenha(dto.login());
        return token;
    }

    public boolean validarRequisicaoSenha(ForgotPasswordDTO dto) {
        Usuario usuario = usuarioRepository.findByLogin(dto.login()).firstResult();

        if (usuario == null || !hashService.verifyArgon2(dto.senhaAtual(), usuario.getSenhaHash())) {
            return false;
        }
        return true;
    }
}
