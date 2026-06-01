package leepans.mapper;

import leepans.dto.cupomDesconto.CupomDescontoEcommerceDTO;
import leepans.dto.cupomDesconto.CupomDescontoRequestDTO;
import leepans.dto.cupomDesconto.CupomDescontoResponseDTO;
import leepans.model.CupomDesconto;

public class CupomDescontoMapper {

    public static CupomDesconto toEntity(CupomDescontoRequestDTO dto) {
        if (dto == null) return null;

        CupomDesconto cupomDesconto = new CupomDesconto();
        cupomDesconto.setCodigo(dto.codigo());
        cupomDesconto.setValorDesconto(dto.valorDesconto());
        cupomDesconto.setPercentualDesconto(dto.percentualDesconto());
        cupomDesconto.setValorMinimoCompra(dto.valorMinimoCompra());
        cupomDesconto.setDataValidade(dto.dataValidade());
        cupomDesconto.setQuantidadeDisponivel(dto.quantidadeDisponivel());
        cupomDesconto.setAtivo(dto.ativo());
        if (dto.version() != null) {
            cupomDesconto.setVersion(dto.version());
        }
        return cupomDesconto;
    }

    public static CupomDescontoResponseDTO toResponse(CupomDesconto cupomDesconto) {
        if (cupomDesconto == null) return null;

        return new CupomDescontoResponseDTO(
                cupomDesconto.getId(),
                cupomDesconto.getCodigo(),
                cupomDesconto.getValorDesconto(),
                cupomDesconto.getPercentualDesconto(),
                cupomDesconto.getValorMinimoCompra(),
                cupomDesconto.getDataValidade(),
                cupomDesconto.getQuantidadeDisponivel(),
                cupomDesconto.isAtivo(),
                cupomDesconto.getDataCadastro(),
                cupomDesconto.getVersion()
        );
    }

    public static CupomDescontoEcommerceDTO toEcommerceDTO(CupomDesconto cupomDesconto) {
        if (cupomDesconto == null) return null;

        return new CupomDescontoEcommerceDTO(
                cupomDesconto.getId(),
                cupomDesconto.getCodigo(),
                cupomDesconto.getValorDesconto(),
                cupomDesconto.getPercentualDesconto(),
                cupomDesconto.getValorMinimoCompra(),
                cupomDesconto.getDataValidade(),
                cupomDesconto.getQuantidadeDisponivel(),
                cupomDesconto.isAtivo()
        );
    }
}
