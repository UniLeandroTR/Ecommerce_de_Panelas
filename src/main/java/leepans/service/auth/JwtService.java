package leepans.service.auth;

import java.util.Set;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Usuario;

@ApplicationScoped
public class JwtService {

    // Tempo de expiração: 1 hora em segundos
    private static final long EXPIRACAO_SEGUNDOS = 3600L;

    /**
     * Gera um token JWT assinado para o usuario autenticado.
     * O perfil é incluido como grupo (claim "groups"), permitindo uso com @RolesAllowed.
     */
    public String gerarToken(Usuario usuario) {
        return Jwt.issuer("Ecommerce-de-Panelas")
                .upn(usuario.getLogin())
                .groups(Set.of(usuario.getPerfil().name()))
                .expiresIn(EXPIRACAO_SEGUNDOS)
                .sign();
    }
}
