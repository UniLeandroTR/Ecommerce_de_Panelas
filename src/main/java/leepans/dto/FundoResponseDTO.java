package leepans.dto;

import leepans.model.Cor;
import leepans.model.Material;

public record FundoResponseDTO(Long id, Double peso, Material material, Cor cor, Double espessura, Boolean isAntiaderente) {
    
}
