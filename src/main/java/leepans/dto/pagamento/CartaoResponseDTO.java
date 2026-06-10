package leepans.dto.pagamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import leepans.model.StatusPagamento;

public record CartaoResponseDTO (
    Long id,
    Long pedidoId,
    LocalDateTime dataProcessado,
    LocalDateTime dataCadastro,
    BigDecimal valor,
    Boolean isCredito,
    StatusPagamento statusPagamento,
    String numero, 
    String titular, 
    LocalDate validade, 
    String codigoSeguranca,
    Integer version
) {
    
}
