package leepans.dto.pedido;

import java.math.BigDecimal;

import leepans.dto.panela.PanelaEcommerceDTO;

public record ItemPedidoResponseDTO(
        Long id,
        PanelaEcommerceDTO panela,
        Integer quantidade,
        BigDecimal valorUnitario
) {
}
