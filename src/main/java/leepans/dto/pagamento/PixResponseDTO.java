package leepans.dto.pagamento;

import java.time.LocalDateTime;

import leepans.model.StatusPagamento;

public record PixResponseDTO (
    Long id,
    Long pedidoId,
    LocalDateTime dataProcessado,
    LocalDateTime dataCadastro,
    Double valor,
    StatusPagamento statusPagamento,
    String chavePix,
    Integer version
) {
    
}
