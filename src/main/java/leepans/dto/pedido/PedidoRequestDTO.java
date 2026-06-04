package leepans.dto.pedido;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import leepans.dto.endereco.EnderecoRequestDTO;
import leepans.model.TipoPagamento;

public record PedidoRequestDTO(

        @NotEmpty(message = "O pedido deve conter pelo menos um item")
        @Valid
        List<ItemPedidoRequestDTO> itens,

        @Valid
        EnderecoRequestDTO endereco,

        String codigoCupomDesconto,

        @NotNull(message = "O tipo de pagamento é obrigatório (PIX, BOLETO, CARTAO_CREDITO ou CARTAO_DEBITO)")
        TipoPagamento  pagamento,

        Integer version
) {
}
