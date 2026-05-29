package leepans.dto.usuario;

import leepans.dto.endereco.EnderecoRequestDTO;

public record CadastroCompletoDTO(
        String nome,
        String sobrenome,
        String login,
        String senha,
        EnderecoRequestDTO endereco) {

}
