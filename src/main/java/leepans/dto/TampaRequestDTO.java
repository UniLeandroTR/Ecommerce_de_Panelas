package leepans.dto;

import jakarta.validation.constraints.NotNull;

public record TampaRequestDTO(
        Double peso,

        @NotNull
        Long idMaterial,

        @NotNull
        Long idCor,

        Boolean isDePressao) {
    
}
