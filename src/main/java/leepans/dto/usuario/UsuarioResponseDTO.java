package leepans.dto.usuario;

import leepans.dto.endereco.EnderecoResponseDTO;
import leepans.model.Perfil;

public record UsuarioResponseDTO (
        Long id,
        String login,
        String nome,
        Perfil perfil,
        EnderecoResponseDTO endereco
){
}
