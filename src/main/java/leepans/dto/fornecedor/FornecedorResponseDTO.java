package leepans.dto.fornecedor;

import java.time.LocalDateTime;

public record FornecedorResponseDTO(Long id, String nome, String telefone, String cnpj, LocalDateTime dataCadastro, Integer version) {
}
