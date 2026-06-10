package leepans.dto.panela;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    LocalDateTime dataCadastro,
    BigDecimal preco,
    Double peso,
    Double capacidadeLitros,
    String descricao,
    boolean isInducao,
    Tamanho tamanho,
    CategoriaResponseDTO categoria,
    ColecaoResponseDTO colecao,
    FornecedorResponseDTO fornecedor,
    TampaResponseDTO tampa,
    FundoResponseDTO fundo,
    SustentacaoResponseDTO sustentacao,
    Integer version
) {
    
}
