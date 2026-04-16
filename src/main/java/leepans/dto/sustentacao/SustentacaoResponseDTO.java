package leepans.dto.sustentacao;

import leepans.dto.cor.CorResponseDTO;
import leepans.dto.material.MaterialResponseDTO;
import leepans.model.TipoSustentacao;

public record SustentacaoResponseDTO(Long id, Double peso, MaterialResponseDTO material, CorResponseDTO cor, Integer tamanhoEmCm, Integer quantidade, TipoSustentacao tipoSustentacao) {
    
}
