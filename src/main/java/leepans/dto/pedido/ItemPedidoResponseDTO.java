package leepans.dto.pedido;

import leepans.dto.panela.PanelaResponseDTO;

public record ItemPedidoResponseDTO(
        Long id,
        PanelaResponseDTO panela,
        Integer quantidade,
        Double valorUnitario
) {
}
