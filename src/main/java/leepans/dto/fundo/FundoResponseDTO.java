package leepans.dto.fundo;

import java.util.List;
import leepans.dto.cor.CorResponseDTO;
import leepans.dto.material.MaterialResponseDTO;

public record FundoResponseDTO(Long id, Double peso, List<MaterialResponseDTO> materiais, CorResponseDTO cor, Double espessura, Boolean isAntiaderente) {
    
}
