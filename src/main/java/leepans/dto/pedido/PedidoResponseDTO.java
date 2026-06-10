package leepans.dto.pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import leepans.dto.cupomDesconto.CupomDescontoResponseDTO;
import leepans.dto.endereco.EnderecoResponseDTO;
import leepans.dto.pagamento.PagamentoResponseDTO;
import leepans.dto.usuario.UsuarioResponseDTO;
import leepans.model.StatusPedido;

public record PedidoResponseDTO(
        Long id,
        UsuarioResponseDTO usuario,
        EnderecoResponseDTO endereco,
        StatusPedido status,
        BigDecimal valorBruto,
        BigDecimal valorDesconto,
        CupomDescontoResponseDTO cupomDesconto,
        PagamentoResponseDTO pagamento,
        List<ItemPedidoResponseDTO> itens,
        LocalDateTime dataCadastro,
        Integer version
) {
}
