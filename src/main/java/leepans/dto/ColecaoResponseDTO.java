package leepans.dto;

import java.util.List;

import leepans.model.Panela;

public record ColecaoResponseDTO(
    Long id,
    String nome,
    List<Panela> panelas
) {
    
}
