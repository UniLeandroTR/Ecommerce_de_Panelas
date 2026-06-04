package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.pagamento.BoletoRequestDTO;
import leepans.dto.pagamento.CartaoRequestDTO;
import leepans.dto.pagamento.PagamentoPatchDTO;
import leepans.dto.pagamento.PixRequestDTO;
import leepans.model.Pagamento;
import leepans.model.StatusPagamento;
import leepans.model.TipoPagamento;

public interface PagamentoServiceInter {

    List<Pagamento> findAll();

    Pagamento findById(Long id);

    List<Pagamento> findByStatusPagamento(StatusPagamento statusPagamento);

    List<Pagamento> findByTipoPagamento(TipoPagamento tipoPagamento);

    List<Pagamento> findByStatusAndTipo(StatusPagamento statusPagamento, TipoPagamento tipoPagamento);

    List<Pagamento> findByValorGreaterThan(Double valor);

    Pagamento create(Pagamento pagamento);

    void completeInfo(Long id, CartaoRequestDTO dto);

    void completeInfo(Long id, BoletoRequestDTO dto);

    void completeInfo(Long id, PixRequestDTO dto);

    void setStatus(Long id, PagamentoPatchDTO dto);

    void delete(Long id);
}
