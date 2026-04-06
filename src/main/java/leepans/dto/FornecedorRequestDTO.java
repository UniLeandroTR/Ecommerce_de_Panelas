package leepans.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FornecedorRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(min = 3, max = 100, message = "O nome do fornecedor deve ter entre 3-100 caracteres")
        String nome,

        @Size(min = 9, max = 19, message = "O telefone deve ter entre 9-19 caracteres")
        String telefone,

        @NotBlank(message = "O CNPJ deve ser preenchido!")
        @Size(min = 14, max = 18, message = "O CNPJ deve ter entre 14 a 18 caracteres")
        String cnpj
) {
}
