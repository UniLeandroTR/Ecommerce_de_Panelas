package leepans.dto.tampa;

import java.time.LocalDateTime;
import java.util.List;

import leepans.dto.material.MaterialResponseDTO;

public record TampaResponseDTO(Long id, Double peso, List<MaterialResponseDTO> materiais, Boolean isDePressao, LocalDateTime dataCadastro, Integer version) {
    
}
