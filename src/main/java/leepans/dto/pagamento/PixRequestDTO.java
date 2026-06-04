package leepans.dto.pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PixRequestDTO(
    @NotBlank(message = "A chave Pix é obrigatória")
    @Size(min = 3, max = 140, message = "A chave Pix deve ter entre 3 e 140 caracteres")
    String chavePix
) {
    
}
