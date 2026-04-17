package leepans.service;

import java.util.List;

import leepans.dto.cor.CorRequestDTO;
import leepans.model.Cor;

public interface CorServiceInter {
    
    List<Cor> findAll();
    Cor findById(Long id);
    List<Cor> findByNome(String nome);
    Cor create(Cor cor);
    void update(Long id, CorRequestDTO dto);
    void delete(Long id);

}
