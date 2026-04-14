package leepans.service;

import java.util.List;

import leepans.dto.colecao.ColecaoRequestDTO;
import leepans.model.Colecao;

public interface ColecaoServiceInter {
    
    List<Colecao> findAll();
    Colecao findById(Long id);
    Colecao create(Colecao colecao);
    void update(Long id, ColecaoRequestDTO dto);
    void delete(Long id);
}
