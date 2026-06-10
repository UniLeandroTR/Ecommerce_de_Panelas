package leepans.service.ecommerce;

import java.math.BigDecimal;
import java.util.List;

import leepans.dto.panela.PanelaRequestDTO;
import leepans.model.Panela;

public interface PanelaServiceInter {
    
    List<Panela> findAll();
    Panela findById(Long id);
    List<Panela> findByCategoria(Long idcategoria);
    List<Panela> findByColecao(Long idcolecao);
    Panela create(Panela panela);
    void setPrice(Long id, BigDecimal preco);
    void update(Long id, PanelaRequestDTO dto);
    void delete(Long id);
}
