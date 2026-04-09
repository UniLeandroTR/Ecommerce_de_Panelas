package leepans.service;

import java.util.List;

import leepans.dto.CategoriaRequestDTO;
import leepans.model.Categoria;

public interface CategoriaServiceInter {
    
    List<Categoria> findAll();
    Categoria findById(Long id);
    Categoria create(Categoria categoria);
    void update(Long id, CategoriaRequestDTO dto);
    void delete(Long id);
}
