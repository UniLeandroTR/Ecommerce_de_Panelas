package leepans.dto.pagamento;

import java.time.LocalDateTime;

import leepans.dto.pedido.PedidoResponseDTO;
import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;

public record PagamentoResponseDTO(
        Long id,
        PedidoResponseDTO pedido,
        LocalDateTime dataProcessado,
        Double valor,
        TipoPagamento tipoPagamento,
        StatusPagamento statusPagamento,
        LocalDateTime dataCadastro,
        Integer version
) {
}
