package leepans.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.pagamento.BoletoResponseDTO;
import leepans.dto.pagamento.CartaoResponseDTO;
import leepans.dto.pagamento.PagamentoEcommerceDTO;
import leepans.dto.pagamento.PagamentoRequestDTO;
import leepans.dto.pagamento.PagamentoResponseDTO;
import leepans.dto.pagamento.PixResponseDTO;
import leepans.model.Boleto;
import leepans.model.CartaoCredito;
import leepans.model.CartaoDebito;
import leepans.model.Pagamento;
import leepans.model.Pix;
import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;

@ApplicationScoped
public class PagamentoMapper {

    @Inject
    PedidoMapper pedidoMapper;

    public Pagamento toEntity(PagamentoRequestDTO dto) {
        if (dto == null)
            return null;
        Pagamento pagamento = null;
        if (dto.tipoPagamento().equals(TipoPagamento.CARTAO_CREDITO))
            pagamento = new CartaoCredito();
        if (dto.tipoPagamento().equals(TipoPagamento.CARTAO_DEBITO))
            pagamento = new CartaoDebito();
        if (dto.tipoPagamento().equals(TipoPagamento.PIX))
            pagamento = new Pix();
        if (dto.tipoPagamento().equals(TipoPagamento.BOLETO))
            pagamento = new Boleto();
        pagamento.setValor(dto.valor());
        pagamento.setTipoPagamento(dto.tipoPagamento());
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamento.setVersion(dto.version());

        return pagamento;
    }

    public PagamentoResponseDTO toResponse(Pagamento pagamento) {
        if (pagamento == null)
            return null;

        return new PagamentoResponseDTO(
                pagamento.getId(),
                pedidoMapper.toResponse(pagamento.getPedido()),
                pagamento.getDataProcessado(),
                pagamento.getValor(),
                pagamento.getTipoPagamento(),
                pagamento.getStatusPagamento(),
                pagamento.getDataCadastro(),
                pagamento.getVersion());
    }

    public CartaoResponseDTO toResponse(CartaoCredito cartao) {
        if (cartao == null)
            return null;

        return new CartaoResponseDTO(
                cartao.getId(),
                pedidoMapper.toResponse(cartao.getPedido()),
                cartao.getDataProcessado(),
                cartao.getDataCadastro(),
                cartao.getValor(),
                true,
                cartao.getStatusPagamento(),
                cartao.getNumero(),
                cartao.getTitular(),
                cartao.getValidade(),
                cartao.getCodigoSeguranca(),
                cartao.getVersion());
    }

    public CartaoResponseDTO toResponse(CartaoDebito cartao) {
        if (cartao == null)
            return null;

        return new CartaoResponseDTO(
                cartao.getId(),
                pedidoMapper.toResponse(cartao.getPedido()),
                cartao.getDataProcessado(),
                cartao.getDataCadastro(),
                cartao.getValor(),
                false,
                cartao.getStatusPagamento(),
                cartao.getNumero(),
                cartao.getTitular(),
                cartao.getValidade(),
                cartao.getCodigoSeguranca(),
                cartao.getVersion());
    }

    public BoletoResponseDTO toResponse(Boleto boleto) {
        if (boleto == null)
            return null;

        return new BoletoResponseDTO(
            boleto.getId(), 
            pedidoMapper.toResponse(boleto.getPedido()), 
            boleto.getDataProcessado(),
            boleto.getDataCadastro(), 
            boleto.getValor(), 
            boleto.getStatusPagamento(), 
            boleto.getCodigoBarras(),
            boleto.getVersion());
    }

    public PixResponseDTO toResponse(Pix pix) {
        if (pix == null)
            return null;

        return new PixResponseDTO(
            pix.getId(), 
            pedidoMapper.toResponse(pix.getPedido()), 
            pix.getDataProcessado(),
            pix.getDataCadastro(), 
            pix.getValor(), 
            pix.getStatusPagamento(), 
            pix.getChavePix(),
            pix.getVersion());
    }



    public PagamentoEcommerceDTO toEcommerceDTO(Pagamento pagamento) {
        if (pagamento == null)
            return null;

        return new PagamentoEcommerceDTO(
                pagamento.getId(),
                pagamento.getDataProcessado(),
                pagamento.getValor(),
                pagamento.getTipoPagamento(),
                pagamento.getStatusPagamento());
    }
}
