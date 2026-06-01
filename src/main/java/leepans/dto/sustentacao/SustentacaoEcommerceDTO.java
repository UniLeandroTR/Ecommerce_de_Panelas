package leepans.dto.sustentacao;

import java.util.List;

import leepans.dto.material.MaterialEcommerceDTO;
import leepans.model.TipoSustentacao;

public record SustentacaoEcommerceDTO(
    Long id,
    List<MaterialEcommerceDTO> materiais,
    Integer quantidade,
    TipoSustentacao tipoSustentacao
) {
    
}
