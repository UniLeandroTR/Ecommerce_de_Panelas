package leepans.dto.panela;

import leepans.dto.categoria.CategoriaEcommerceDTO;
import leepans.dto.colecao.ColecaoEcommerceDTO;
import leepans.dto.cor.CorEcommerceDTO;
import leepans.dto.fundo.FundoEcommerceDTO;
import leepans.dto.material.MaterialEcommerceDTO;
import leepans.dto.sustentacao.SustentacaoEcommerceDTO;
import leepans.dto.tampa.TampaEcommerceDTO;
import leepans.model.Tamanho;

public record PanelaEcommerceDTO(
    Long id,
    String modelo,
    Double preco,
    Double capacidadeLitros,
    boolean isInducao,
    Tamanho tamanho,
    MaterialEcommerceDTO material,
    CorEcommerceDTO cor,
    CategoriaEcommerceDTO categoria,
    ColecaoEcommerceDTO colecao,
    TampaEcommerceDTO tampa,
    FundoEcommerceDTO fundo,
    SustentacaoEcommerceDTO sustentacao,
    Integer version
) {
    
}
