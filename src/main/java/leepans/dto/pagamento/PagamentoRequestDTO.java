package leepans.dto.pagamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import leepans.model.TipoPagamento;

public record PagamentoRequestDTO(
        @NotNull(message = "O ID do pedido é obrigatório")
        Long pedidoId,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser positivo")
        Double valor,

        @NotNull(message = "O tipo de pagamento é obrigatório")
        TipoPagamento tipoPagamento,

        Integer version
) {
}
