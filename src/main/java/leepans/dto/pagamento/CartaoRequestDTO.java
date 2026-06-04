package leepans.dto.pagamento;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CartaoRequestDTO(
    @NotBlank(message = "O número do cartão é obrigatório")
    @Size(min = 13, max = 19, message = "O número do cartão deve ter entre 13 e 19 dígitos")
    String numero, 

    @NotBlank(message = "O titular do cartão é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do titular deve ter entre 3 e 100 caracteres")
    String titular, 

    @NotNull(message = "A data de validade é obrigatória")
    @FutureOrPresent(message = "A data de validade do cartão não pode estar no passado")
    LocalDate validade, 

    @NotBlank(message = "O código de segurança é obrigatório")
    @Size(min = 3, max = 4, message = "O código de segurança deve ter 3 ou 4 dígitos")
    @Pattern(regexp = "^[0-9]+$", message = "O código de segurança deve conter apenas dígitos")
    String codigoSeguranca,

    @NotNull(message = "O tipo de cartão (crédito/débito) é obrigatório")
    Boolean isCredito
) {
    
}
