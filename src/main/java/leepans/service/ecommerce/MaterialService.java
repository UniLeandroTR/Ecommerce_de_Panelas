package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.material.MaterialRequestDTO;
import leepans.model.Material;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class MaterialService implements MaterialServiceInter{

    @Inject
    MaterialRepository repository;

    @Override
    public List<Material> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Material findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Material> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Material create(Material material) {
        repository.persist(material);
        return material;
    }

    @Override
    @Transactional
    public void update(Long id, MaterialRequestDTO dto) {
        Material material = repository.findById(id);
        if (material.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                "Conflito de concorrência: o material foi alterado por outra transação."
            );
        }
        if (material != null) {
            material.setNome(dto.nome());
            material.setQualidades(dto.qualidades());
            repository.persist(material);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
