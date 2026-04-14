package leepans.dto;

import leepans.model.Cor;
import leepans.model.Material;

public record FundoResponseDTO(Long id, Double peso, String material, String cor, Double espessura, Boolean isAntiaderente) {
    
}
