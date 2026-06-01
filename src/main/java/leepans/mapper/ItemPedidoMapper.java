package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.pedido.ItemPedidoRequestDTO;
import leepans.dto.pedido.ItemPedidoResponseDTO;
import leepans.model.ItemPedido;
import leepans.repository.PanelaRepository;

@ApplicationScoped
public class ItemPedidoMapper {

    @Inject
    PanelaMapper panelaMapper;

    @Inject
    PanelaRepository panelaRepository;

    public ItemPedido toEntity(ItemPedidoRequestDTO dto) {
        if (dto == null) return null;

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQuantidade(dto.quantidade());
        itemPedido.setValorUnitario(dto.valorUnitario());
        if (dto.idPanela() != null) {
            itemPedido.setPanela(panelaRepository.findById(dto.idPanela()));
        }
        return itemPedido;
    }

    public ItemPedidoResponseDTO toResponse(ItemPedido itemPedido) {
        if (itemPedido == null) return null;

        return new ItemPedidoResponseDTO(
                itemPedido.getId(),
                panelaMapper.toEcommerceDTO(itemPedido.getPanela()),
                itemPedido.getQuantidade(),
                itemPedido.getValorUnitario()
        );
    }
}
