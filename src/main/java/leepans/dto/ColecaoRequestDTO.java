package leepans.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import leepans.model.Panela;

public record ColecaoRequestDTO(

    @NotBlank(message = "Um nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome da coleção deve ter entre 3 a 100 caracteres")
    String nome,
    List<Panela> panelas
) {
    
}
