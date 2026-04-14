package leepans.dto;

import java.util.List;

import leepans.model.Categoria;
import leepans.model.Colecao;
import leepans.model.Fornecedor;
import leepans.model.Fundo;
import leepans.model.Sustentacao;
import leepans.model.Tamanho;
import leepans.model.Tampa;

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
