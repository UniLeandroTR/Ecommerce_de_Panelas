package leepans.dto.panela;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import leepans.model.Tamanho;

public record PanelaRequestDTO(

    @NotBlank
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 a 100 caracteres")
    String modelo,
   
    Long idColecao,
    
    @NotNull(message = "É necessário escolher uma categoria")
    Long idCategoria,
    
    Tamanho tamanho,
    
    @Min(value = 0, message = "O preço não pode ser negativo ou menor que zero")
    Long preco,
    
    Double peso,
    
    Double capacidadeLitros,
    
    List<String> funcionalidades,
    
    boolean isInducao,
    
    @NotNull(message = "É necessário escolher um fornecedor")
    Long idFornecedor,
    
    @NotNull(message = "É necessário escolher uma tampa")
    Long idTampa,
    
    @NotNull(message = "É necessário escolher um fundo")
    Long idFundo,

    @NotNull(message = "É necessário escolher uma sustentação")
    Long idSustentacao
) {
    
}
