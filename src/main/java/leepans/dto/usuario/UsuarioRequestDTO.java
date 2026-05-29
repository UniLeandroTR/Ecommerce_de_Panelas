package leepans.dto.usuario;

import leepans.model.Perfil;

public record UsuarioRequestDTO(
        String login,
        String senha,
        Perfil perfil,
        Long idEndereco,
        Integer version
) {
}
