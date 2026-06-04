package leepans.dto.pedido;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import leepans.model.TipoPagamento;

public record PedidoRequestDTO(

        @NotEmpty(message = "O pedido deve conter pelo menos um item")
        @Valid
        List<ItemPedidoRequestDTO> itens,

        String codigoCupomDesconto,

        TipoPagamento  pagamento,

        Integer version
) {
}
