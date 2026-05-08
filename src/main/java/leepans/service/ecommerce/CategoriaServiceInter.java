package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.categoria.CategoriaRequestDTO;
import leepans.model.Categoria;

public interface CategoriaServiceInter {
    
    List<Categoria> findAll();
    Categoria findById(Long id);
    List<Categoria> findByNome(String nome);
    Categoria create(Categoria categoria);
    void update(Long id, CategoriaRequestDTO dto);
    void delete(Long id);
}
