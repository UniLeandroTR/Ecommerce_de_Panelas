package leepans.dto.tampa;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record TampaRequestDTO(
        Double peso,

        @NotNull
        List<Long> idsMateriais,

        Boolean isDePressao,

        Integer version) {
    
}
