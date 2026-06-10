package leepans.dto.pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;

public record PagamentoEcommerceDTO(
        Long id,
        LocalDateTime dataProcessado,
        BigDecimal valor,
        TipoPagamento tipoPagamento,
        StatusPagamento statusPagamento
) {
}
