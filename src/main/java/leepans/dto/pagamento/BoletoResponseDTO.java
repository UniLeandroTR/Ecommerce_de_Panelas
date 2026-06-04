package leepans.dto.pagamento;

import java.time.LocalDateTime;

import leepans.dto.pedido.PedidoResponseDTO;
import leepans.model.StatusPagamento;

public record BoletoResponseDTO(
    Long id,
        PedidoResponseDTO pedido,
        LocalDateTime dataProcessado,
        LocalDateTime dataCadastro,
        Double valor,
        StatusPagamento statusPagamento,
        String codigoBarras,
        Integer version
) {
    
}
