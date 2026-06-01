package leepans.dto.tampa;

import java.util.List;

import leepans.dto.material.MaterialEcommerceDTO;

public record TampaEcommerceDTO(
    Long id,
    List<MaterialEcommerceDTO> materiais,
    Boolean isDePressao
) {
    
}
