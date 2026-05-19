package leepans.dto.panela;

import java.time.LocalDateTime;

import leepans.dto.categoria.CategoriaResponseDTO;
import leepans.dto.colecao.ColecaoResponseDTO;
import leepans.dto.cor.CorResponseDTO;
import leepans.dto.fundo.FundoResponseDTO;
import leepans.dto.material.MaterialResponseDTO;
import leepans.dto.sustentacao.SustentacaoResponseDTO;
import leepans.dto.tampa.TampaResponseDTO;
import leepans.model.Tamanho;

public record PanelaEcommerceDTO(
    Long id,
    String modelo,
    LocalDateTime dataCadastro,
    Long preco,
    Double capacidadeLitros,
    boolean isInducao,
    Tamanho tamanho,
    MaterialResponseDTO material,
    CorResponseDTO cor,
    CategoriaResponseDTO categoria,
    ColecaoResponseDTO colecao,
    TampaResponseDTO tampa,
    FundoResponseDTO fundo,
    SustentacaoResponseDTO sustentacao,
    Integer version
) {
    
}
