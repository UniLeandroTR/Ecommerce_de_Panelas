package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.pagamento.PagamentoPatchDTO;
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

    void setStatus(Long id, PagamentoPatchDTO dto);

    void delete(Long id);
}
