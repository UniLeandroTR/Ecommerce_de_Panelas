package leepans.dto.cupomDesconto;

import java.time.LocalDateTime;

public record CupomDescontoEcommerceDTO(
        Long id,
        String codigo,
        Double valorDesconto,
        Double percentualDesconto,
        Double valorMinimoCompra,
        LocalDateTime dataValidade,
        Integer quantidadeDisponivel,
        boolean ativo
) {
}
