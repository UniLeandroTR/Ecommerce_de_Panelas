package leepans.dto;

import leepans.model.Cor;
import leepans.model.Material;

public record TampaResponseDTO(Long id, Double peso, String material, String cor, Boolean isDePressao) {
    
}
