package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.pedido.ItemPedidoRequestDTO;
import leepans.dto.pedido.ItemPedidoResponseDTO;
import leepans.model.ItemPedido;

@ApplicationScoped
public class ItemPedidoMapper {

    @Inject
    PanelaMapper panelaMapper;

    public ItemPedido toEntity(ItemPedidoRequestDTO dto) {
        if (dto == null) return null;

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQuantidade(dto.quantidade());
        itemPedido.setValorUnitario(dto.valorUnitario());
        return itemPedido;
    }

    public ItemPedidoResponseDTO toResponse(ItemPedido itemPedido) {
        if (itemPedido == null) return null;

        return new ItemPedidoResponseDTO(
                itemPedido.getId(),
                panelaMapper.toResponseDTO(itemPedido.getPanela()),
                itemPedido.getQuantidade(),
                itemPedido.getValorUnitario()
        );
    }
}
