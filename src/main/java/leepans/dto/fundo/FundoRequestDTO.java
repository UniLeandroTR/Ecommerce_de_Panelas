package leepans.dto.fundo;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record FundoRequestDTO(
        Double peso,

        @NotNull List<Long> idsMateriais,
        Double espessura,

        Boolean isAntiaderente,

        Integer version) {

}
