package leepans.dto.pagamento;

import java.time.LocalDateTime;

import leepans.model.StatusPagamento;

public record BoletoResponseDTO(
        Long id,
        Long pedidoId,
        LocalDateTime dataProcessado,
        LocalDateTime dataCadastro,
        Double valor,
        StatusPagamento statusPagamento,
        String codigoBarras,
        Integer version) {

}
