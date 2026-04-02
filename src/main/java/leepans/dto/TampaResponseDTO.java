package leepans.dto;

import leepans.model.Cor;
import leepans.model.Material;

public record TampaResponseDTO(Long id, Double peso, Material material, Cor cor, Boolean isDePressao) {
    
}
