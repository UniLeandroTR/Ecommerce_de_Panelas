package leepans.dto.listaDesejo;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ListaDesejoRequestDTO (
        @NotNull(message = "É necessário informar um usuário")
        Long idUsuario,

        List<Long> idPanelas,
        Integer version
) {
}
