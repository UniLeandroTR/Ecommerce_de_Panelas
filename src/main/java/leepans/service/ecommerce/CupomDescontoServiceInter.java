package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.cupomDesconto.CupomDescontoRequestDTO;
import leepans.model.CupomDesconto;

public interface CupomDescontoServiceInter {

    List<CupomDesconto> findAll();

    CupomDesconto findById(Long id);

    List<CupomDesconto> findByAtivo(boolean ativo);

    CupomDesconto findByCodigo(String codigo);

    List<CupomDesconto> findByAtivoAndValorMinimoCompra(boolean ativo, Double valorMinimo);

    CupomDesconto create(CupomDesconto cupomDesconto);

    void update(Long id, CupomDescontoRequestDTO dto);

    void decrementarQuantidade(CupomDesconto cupomDesconto);

    void incrementarQuantidade(CupomDesconto cupomDesconto);

    void delete(Long id);
}
