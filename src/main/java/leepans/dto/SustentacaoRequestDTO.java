package leepans.dto;

import jakarta.validation.constraints.NotNull;
import leepans.model.TipoSustentacao;

public record SustentacaoRequestDTO(
        Double peso,

        @NotNull
        Long idMaterial,

        @NotNull
        Long idCor,

        Integer tamanhoEmCm,

        Integer quantidade,

        TipoSustentacao tipoSustentacao) {
    
}
