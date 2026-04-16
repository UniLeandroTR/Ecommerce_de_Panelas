package leepans.dto.tampa;

import leepans.dto.cor.CorResponseDTO;
import leepans.dto.material.MaterialResponseDTO;

public record TampaResponseDTO(Long id, Double peso, MaterialResponseDTO material, CorResponseDTO cor, Boolean isDePressao) {
    
}
