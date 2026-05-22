package leepans.dto.usuario;

import leepans.model.Panela;
import leepans.model.Perfil;

import java.util.List;

public record UsuarioResponseDTO (
        Long id,
        String login,
        String senha,
        Perfil perfil
){
}
