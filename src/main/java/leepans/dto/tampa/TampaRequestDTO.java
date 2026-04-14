package leepans.dto.tampa;

import jakarta.validation.constraints.NotNull;

public record TampaRequestDTO(
        Double peso,

        @NotNull
        Long idMaterial,

        @NotNull
        Long idCor,

        Boolean isDePressao) {
    
}
