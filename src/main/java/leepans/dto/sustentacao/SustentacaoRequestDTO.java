package leepans.dto.sustentacao;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import leepans.model.TipoSustentacao;

public record SustentacaoRequestDTO(
        Double peso,

        @NotNull
        List<Long> idsMateriais,

        Integer tamanhoEmCm,

        Integer quantidade,

        TipoSustentacao tipoSustentacao,

        Integer version) {
    
}
