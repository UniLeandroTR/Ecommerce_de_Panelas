package leepans.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

import leepans.dto.endereco.EnderecoResponseDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.model.StatusPedido;

public record PedidoResponseDTO(
        Long id,
        UsuarioResponseDTO usuario,
        EnderecoResponseDTO endereco,
        StatusPedido status,
        Double valorTotal,
        List<ItemPedidoResponseDTO> itens,
        LocalDateTime dataCadastro,
        Integer version
) {
}
