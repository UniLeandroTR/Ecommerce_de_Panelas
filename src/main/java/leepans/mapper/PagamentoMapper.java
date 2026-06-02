package leepans.mapper;

import leepans.dto.pagamento.PagamentoEcommerceDTO;
import leepans.dto.pagamento.PagamentoRequestDTO;
import leepans.dto.pagamento.PagamentoResponseDTO;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;

public class PagamentoMapper {

    public static Pagamento toEntity(PagamentoRequestDTO dto) {
        if (dto == null) return null;

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.valor());
        pagamento.setTipoPagamento(dto.tipoPagamento());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setVersion(dto.version());

        return pagamento;
    }

    public static PagamentoResponseDTO toResponse(Pagamento pagamento) {
        if (pagamento == null) return null;

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pagamento.getDataProcessado(),
                pagamento.getValor(),
                pagamento.getTipoPagamento(),
                pagamento.getStatusPagamento(),
                pagamento.getDataCadastro(),
                pagamento.getVersion()
        );
    }

    public static PagamentoEcommerceDTO toEcommerceDTO(Pagamento pagamento) {
        if (pagamento == null) return null;

        return new PagamentoEcommerceDTO(
                pagamento.getId(),
                pagamento.getDataProcessado(),
                pagamento.getValor(),
                pagamento.getTipoPagamento(),
                pagamento.getStatusPagamento()
        );
    }
}
