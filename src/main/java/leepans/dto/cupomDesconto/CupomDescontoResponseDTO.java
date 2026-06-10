package leepans.dto.cupomDesconto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CupomDescontoResponseDTO(
        Long id,
        String codigo,
        BigDecimal valorDesconto,
        BigDecimal percentualDesconto,
        BigDecimal valorMinimoCompra,
        LocalDateTime dataValidade,
        Integer quantidadeDisponivel,
        boolean ativo,
        LocalDateTime dataCadastro,
        Integer version
) {
}
