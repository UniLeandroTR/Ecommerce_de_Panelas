package leepans.dto.sustentacao;

import java.util.List;
import leepans.dto.cor.CorResponseDTO;
import leepans.dto.material.MaterialResponseDTO;
import leepans.model.TipoSustentacao;

public record SustentacaoResponseDTO(Long id, Double peso, List<MaterialResponseDTO> materiais, CorResponseDTO cor, Integer tamanhoEmCm, Integer quantidade, TipoSustentacao tipoSustentacao) {
    
}
