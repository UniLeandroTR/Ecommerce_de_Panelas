package leepans.dto.pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import leepans.model.StatusPagamento;

public record BoletoResponseDTO(
        Long id,
        Long pedidoId,
        LocalDateTime dataProcessado,
        LocalDateTime dataCadastro,
        BigDecimal valor,
        StatusPagamento statusPagamento,
        String codigoBarras,
        Integer version) {

}
