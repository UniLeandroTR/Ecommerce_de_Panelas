package leepans.dto.tampa;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record TampaRequestDTO(
        Double peso,

        @NotNull
        List<Long> idsMateriais,

        @NotNull
        Long idCor,

        Boolean isDePressao) {
    
}
