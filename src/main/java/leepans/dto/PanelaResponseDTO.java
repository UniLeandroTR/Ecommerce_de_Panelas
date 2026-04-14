package leepans.dto;

import java.util.List;

import leepans.model.Tamanho;

public record PanelaResponseDTO(
    Long id,
    String modelo,
    String Colecao,
    String Categoria,
    Tamanho tamanho,
    Long preco,
    Double peso,
    Double capacidadeLitros,
    List<String> funcionalidades,
    boolean isInducao,
    String Fornecedor,
    Long idTampa,
    Long idFundo,
    Long idSustentacao
) {
    
}
