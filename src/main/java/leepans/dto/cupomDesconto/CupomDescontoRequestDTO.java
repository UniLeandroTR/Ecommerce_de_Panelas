package leepans.dto.cupomDesconto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CupomDescontoRequestDTO(
        @NotBlank(message = "O código do cupom é obrigatório")
        @Size(min = 3, max = 50, message = "O código deve ter entre 3-50 caracteres")
        String codigo,

        @DecimalMin(value = "0.01", message = "O valor do desconto deve ser positivo")
        BigDecimal valorDesconto,

        @DecimalMin(value = "0.01", message = "O percentual do desconto deve ser positivo")
        BigDecimal percentualDesconto,

        @NotNull(message = "O valor mínimo de compra é obrigatório")
        @DecimalMin(value = "0.0", inclusive = true, message = "O valor mínimo de compra não pode ser negativo")
        BigDecimal valorMinimoCompra,

        @NotNull(message = "A data de validade é obrigatória")
        LocalDateTime dataValidade,

        @NotNull(message = "A quantidade disponível é obrigatória")
        Integer quantidadeDisponivel,

        @NotNull(message = "O status de ativo é obrigatório")
        boolean ativo,

        Integer version
) {
}
