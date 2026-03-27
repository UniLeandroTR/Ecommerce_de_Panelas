package leepans.dto;

import leepans.model.TipoSustentacao;

public record SustentacaoRequestDTO(Double peso, Long idMaterial, Long idCor, Integer tamanhoEmCm, Integer quantidade, TipoSustentacao tipoSustentacao) {
    
}
