package leepans.mapper;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.pedido.PedidoRequestDTO;
import leepans.dto.pedido.PedidoResponseDTO;
import leepans.model.ItemPedido;
import leepans.model.Pedido;
import leepans.model.StatusPedido;
import leepans.repository.CupomDescontoRepository;

@ApplicationScoped
public class PedidoMapper {

    @Inject
    ItemPedidoMapper itemPedidoMapper;

    @Inject
    UsuarioMapper usuarioMapper;

    @Inject
    PagamentoMapper pagamentoMapper;

    @Inject
    CupomDescontoRepository cupomRepository;

    public Pedido toEntity(PedidoRequestDTO dto) {
        if (dto == null) return null;

        Pedido pedido = new Pedido();
        pedido.setStatus(StatusPedido.PENDENTE);
        List<ItemPedido> itens = dto.itens().stream()
                .map(itemPedidoMapper::toEntity)
                .toList();
        pedido.setItens(itens);
        if (dto.codigoCupomDesconto() != null) {
            try{
                pedido.setCupomDesconto(cupomRepository.findByCodigo(dto.codigoCupomDesconto()).firstResult());
            } catch (Exception e) {
                throw new RuntimeException("Cupom de desconto não encontrado: " + dto.codigoCupomDesconto());
            }
        }
        if (dto.version() != null) {
            pedido.setVersion(dto.version());
        }
        return pedido;
    }

    public PedidoResponseDTO toResponse(Pedido pedido) {
        if (pedido == null) return null;

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getUsuario() != null ? usuarioMapper.toResponseDTO(pedido.getUsuario()) : null,
                pedido.getEndereco() != null ? EnderecoMapper.toResponse(pedido.getEndereco()) : null,
                pedido.getStatus(),
                pedido.getValorTotal(),
                pedido.getValorDesconto(),
                CupomDescontoMapper.toResponse(pedido.getCupomDesconto()),
                pagamentoMapper.toResponse(pedido.getPagamento()),
                pedido.getItens() != null ? pedido.getItens().stream()
                        .map(itemPedidoMapper::toResponse)
                        .toList() : null,
                pedido.getDataCadastro(),
                pedido.getVersion()
        );
    }
}
