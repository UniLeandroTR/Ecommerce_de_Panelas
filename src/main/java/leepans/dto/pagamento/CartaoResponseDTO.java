package leepans.dto.pagamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

import leepans.dto.pedido.PedidoResponseDTO;
import leepans.model.StatusPagamento;

public record CartaoResponseDTO (
    Long id,
    PedidoResponseDTO pedido,
    LocalDateTime dataProcessado,
    LocalDateTime dataCadastro,
    Double valor,
    Boolean isCredito,
    StatusPagamento statusPagamento,
    String numero, 
    String titular, 
    LocalDate validade, 
    String codigoSeguranca,
    Integer version
) {
    
}
