package leepans.dto.sustentacao;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import leepans.model.TipoSustentacao;

public record SustentacaoRequestDTO(
        Double peso,

        @NotNull
        List<Long> idsMateriais,

        @NotNull
        Long idCor,

        Integer tamanhoEmCm,

        Integer quantidade,

        TipoSustentacao tipoSustentacao) {
    
}
