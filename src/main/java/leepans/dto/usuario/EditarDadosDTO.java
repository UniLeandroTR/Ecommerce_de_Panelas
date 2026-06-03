package leepans.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import leepans.dto.endereco.EnderecoRequestDTO;

public record EditarDadosDTO(
    @NotBlank(message = "O nome não pode ser vazio")
    String nome,
    @NotBlank(message = "O sobrenome não pode ser vazio")
    String sobrenome,
    EnderecoRequestDTO endereco
) {
    
}
