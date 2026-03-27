package leepans.dto;

import leepans.model.Cor;
import leepans.model.Material;
import leepans.model.TipoSustentacao;

public record SustentacaoResponseDTO(Long id, Double peso, Material material, Cor cor, Integer tamanhoEmCm, Integer quantidade, TipoSustentacao tipoSustentacao) {
    
}
