package leepans.dto.cor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CorRequestDTO(
        @NotBlank
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
        String nome) {
    
}
