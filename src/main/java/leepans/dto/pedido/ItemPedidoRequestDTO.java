package leepans.dto.pedido;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(
        @NotNull(message = "O ID da panela é obrigatório")
        Long idPanela,

        @NotNull(message = "A quantidade é obrigatória")
        @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
        Integer quantidade,

        @NotNull(message = "O valor unitário é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O valor unitário não pode ser negativo")
        BigDecimal valorUnitario
) {
}
