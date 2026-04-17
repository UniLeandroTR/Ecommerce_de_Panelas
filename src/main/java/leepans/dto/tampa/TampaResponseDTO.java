package leepans.dto.tampa;

import java.util.List;
import leepans.dto.cor.CorResponseDTO;
import leepans.dto.material.MaterialResponseDTO;

public record TampaResponseDTO(Long id, Double peso, List<MaterialResponseDTO> materiais, CorResponseDTO cor, Boolean isDePressao) {
    
}
