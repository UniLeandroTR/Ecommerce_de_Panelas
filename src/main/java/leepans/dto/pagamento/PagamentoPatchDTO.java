package leepans.dto.pagamento;

import jakarta.validation.constraints.NotNull;
import leepans.model.StatusPagamento;

public record PagamentoPatchDTO(
    @NotNull(message = "O status do pagamento é obrigatório")
    StatusPagamento statusPagamento,
    Integer version
) {
    
}
