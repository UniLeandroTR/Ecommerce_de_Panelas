package leepans.dto.usuario;

import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.model.Perfil;

public record UsuarioRequestDTO(
        String login,
        String senha,
        Perfil perfil,
        EnderecoRequestDTO endereco,
        Integer version
) {
}
