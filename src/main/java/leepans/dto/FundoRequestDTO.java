package leepans.dto;

import jakarta.validation.constraints.NotNull;

public record FundoRequestDTO(
        Double peso,

        @NotNull
        Long idMaterial,

        @NotNull
        Long idCor,

        Double espessura,

        Boolean isAntiaderente) {
    
}
