package leepans.dto.usuario;

import leepans.model.Perfil;

public record UsuarioResponseDTO (
        Long id,
        String login,
        String senha,
        Perfil perfil
){
}
