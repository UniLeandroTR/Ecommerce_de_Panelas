package leepans.dto.usuario;

import leepans.model.Panela;
import leepans.model.Perfil;

import java.util.List;

public record UsuarioRequestDTO(
        String login,
        String senha,
        Perfil perfil,
        Integer version
) {
}
