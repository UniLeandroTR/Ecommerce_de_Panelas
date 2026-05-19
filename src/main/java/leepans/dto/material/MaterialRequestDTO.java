package leepans.dto.material;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record MaterialRequestDTO(
        @NotBlank
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
        String nome,

        List<String> qualidades,

        Integer version) {
    
}
