package leepans.dto.pagamento;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import leepans.model.TipoPagamento;

public record PagamentoRequestDTO(
        @NotNull(message = "O ID do pedido é obrigatório")
        Long pedidoId,

        @NotNull(message = "O valor é obrigatório")
        @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior que zero") // Alterado para BigDecimal e @DecimalMin
        BigDecimal valor,

        @NotNull(message = "O tipo de pagamento é obrigatório")
        TipoPagamento tipoPagamento,

        Integer version
) {
}
