package leepans.dto.panela;

import java.util.List;

import leepans.dto.categoria.CategoriaResponseDTO;
import leepans.dto.colecao.ColecaoResponseDTO;
import leepans.dto.fornecedor.FornecedorResponseDTO;
import leepans.dto.fundo.FundoResponseDTO;
import leepans.dto.sustentacao.SustentacaoResponseDTO;
import leepans.dto.tampa.TampaResponseDTO;
import leepans.model.Tamanho;

public record PanelaResponseDTO(
    Long id,
    String modelo,
    CategoriaResponseDTO categoria,
    ColecaoResponseDTO colecao,
    Tamanho tamanho,
    Long preco,
    Double peso,
    Double capacidadeLitros,
    String descricao,
    boolean isInducao,
    FornecedorResponseDTO fornecedor,
    TampaResponseDTO tampa,
    FundoResponseDTO fundo,
    SustentacaoResponseDTO sustentacao
) {
    
}
