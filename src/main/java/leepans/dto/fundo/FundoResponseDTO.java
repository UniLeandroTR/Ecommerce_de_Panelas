package leepans.dto.fundo;

import leepans.dto.cor.CorResponseDTO;
import leepans.dto.material.MaterialResponseDTO;

public record FundoResponseDTO(Long id, Double peso, MaterialResponseDTO material, CorResponseDTO cor, Double espessura, Boolean isAntiaderente) {
    
}
