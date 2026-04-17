package leepans.dto.fundo;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record FundoRequestDTO(
        Double peso,

        @NotNull
        List<Long> idsMateriais,

        @NotNull
        Long idCor,

        Double espessura,

        Boolean isAntiaderente) {
    
}
