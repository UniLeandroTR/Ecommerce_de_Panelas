package leepans.dto.pedido;

import leepans.dto.panela.PanelaEcommerceDTO;

public record ItemPedidoResponseDTO(
        Long id,
        PanelaEcommerceDTO panela,
        Integer quantidade,
        Double valorUnitario
) {
}
