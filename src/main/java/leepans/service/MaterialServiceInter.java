package leepans.service;

import java.util.List;

import leepans.dto.material.MaterialRequestDTO;
import leepans.model.Material;

public interface MaterialServiceInter {
    
    List<Material> findAll();
    Material findById(Long id);
    Material create(Material material);
    void update(Long id, MaterialRequestDTO dto);
    void delete(Long id);
}
