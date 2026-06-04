package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.fundo.FundoRequestDTO;
import leepans.model.Fundo;

public interface FundoServiceInter {
    
    List<Fundo> findAll();
    Fundo findById(Long id);
    Fundo create(Fundo fundo);
    void update(Long id, FundoRequestDTO dto);
    void delete(Long id);

}
