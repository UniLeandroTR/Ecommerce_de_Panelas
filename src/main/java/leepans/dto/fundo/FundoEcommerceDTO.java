package leepans.dto.fundo;

import java.util.List;

import leepans.dto.material.MaterialEcommerceDTO;

public record FundoEcommerceDTO(
    Long id,
    List<MaterialEcommerceDTO> materiais,
    Boolean isAntiaderente
) {
    
}
