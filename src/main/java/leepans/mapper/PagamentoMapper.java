package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.pagamento.PagamentoEcommerceDTO;
import leepans.dto.pagamento.PagamentoRequestDTO;
import leepans.dto.pagamento.PagamentoResponseDTO;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;

@ApplicationScoped
public class PagamentoMapper {

    @Inject
    PedidoMapper pedidoMapper;

    public static Pagamento toEntity(PagamentoRequestDTO dto) {
        if (dto == null) return null;

        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.valor());
        pagamento.setTipoPagamento(dto.tipoPagamento());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setVersion(dto.version());

        return pagamento;
    }

    public PagamentoResponseDTO toResponse(Pagamento pagamento) {
        if (pagamento == null) return null;

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pedidoMapper.toResponse(pagamento.getPedido()),
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
