package leepans.dto.panela;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import leepans.model.Tamanho;

public record PanelaRequestDTO(

    @NotBlank
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
    String modelo,

    @Min(value = 0, message = "O preço não pode ser negativo ou menor que zero")
    Double preco,

    Double peso,

    Double capacidadeLitros,

    String descricao,

    boolean isInducao,

    Tamanho tamanho,

    Long idColecao,

    @NotNull(message = "É necessário escolher uma cor")
    Long idCor,

    @NotNull(message = "É necessário escolher um material principal")
    Long idMaterialPrincipal,
    
    @NotNull(message = "É necessário escolher uma categoria")
    Long idCategoria,

    @NotNull(message = "É necessário escolher um fornecedor")
    Long idFornecedor,

    Long idTampa,
    
    @NotNull(message = "É necessário escolher um fundo")
    Long idFundo,

    @NotNull(message = "É necessário escolher uma sustentação")
    Long idSustentacao,
    
    Integer version
) {
    
}
