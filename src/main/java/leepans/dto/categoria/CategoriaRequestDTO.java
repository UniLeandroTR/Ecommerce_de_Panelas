package leepans.dto.categoria;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(
        @NotBlank(message = "O tipo de categoria é obrigatório")
        @Size(min = 3, max = 100, message = "O tipo deve ter entre 3-100 caracteres")
        String tipo,
        Integer version
) {
}
