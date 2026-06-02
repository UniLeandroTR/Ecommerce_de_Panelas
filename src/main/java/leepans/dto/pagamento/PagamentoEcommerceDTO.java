package leepans.dto.pagamento;

import java.time.LocalDateTime;

import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;

public record PagamentoEcommerceDTO(
        Long id,
        LocalDateTime dataProcessado,
        Double valor,
        TipoPagamento tipoPagamento,
        StatusPagamento statusPagamento
) {
}
