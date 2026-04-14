package leepans.dto.sustentacao;

import leepans.model.TipoSustentacao;

public record SustentacaoResponseDTO(Long id, Double peso, String material, String cor, Integer tamanhoEmCm, Integer quantidade, TipoSustentacao tipoSustentacao) {
    
}
