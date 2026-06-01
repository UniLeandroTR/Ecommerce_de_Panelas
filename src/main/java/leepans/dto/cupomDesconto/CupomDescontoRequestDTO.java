package leepans.dto.cupomDesconto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CupomDescontoRequestDTO(
        @NotBlank(message = "O código do cupom é obrigatório")
        @Size(min = 3, max = 50, message = "O código deve ter entre 3-50 caracteres")
        String codigo,

        @Positive(message = "O valor do desconto deve ser positivo")
        Double valorDesconto,

        @Positive(message = "O percentual do desconto deve ser positivo")
        Double percentualDesconto,

        @NotNull(message = "O valor mínimo de compra é obrigatório")
        Double valorMinimoCompra,

        @NotNull(message = "A data de validade é obrigatória")
        LocalDateTime dataValidade,

        @NotNull(message = "A quantidade disponível é obrigatória")
        Integer quantidadeDisponivel,

        @NotNull(message = "O status de ativo é obrigatório")
        boolean ativo,

        Integer version
) {
}
