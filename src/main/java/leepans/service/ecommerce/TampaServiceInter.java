package leepans.service.ecommerce;

import java.util.List;

import leepans.dto.tampa.TampaRequestDTO;
import leepans.model.Tampa;

public interface TampaServiceInter {
    
    List<Tampa> findAll();
    Tampa findById(Long id);
    List<Tampa> findByMaterial(Long idmaterial);
    Tampa create(Tampa tampa);
    void update(Long id, TampaRequestDTO dto);
    void delete(Long id);
}
