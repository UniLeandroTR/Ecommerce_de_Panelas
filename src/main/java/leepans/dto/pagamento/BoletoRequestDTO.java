package leepans.dto.pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record BoletoRequestDTO(
    @NotBlank(message = "O código de barras é obrigatório")
    @Size(min = 47, max = 48, message = "O código de barras deve ter 47 ou 48 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "O código de barras deve conter apenas dígitos")
    String codigoBarras
) {
    
}
