package leepans.service.ecommerce;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.categoria.CategoriaRequestDTO;
import leepans.model.Categoria;
import leepans.repository.CategoriaRepository;

import java.util.List;

@ApplicationScoped
public class CategoriaService implements CategoriaServiceInter {

    @Inject
    CategoriaRepository repository;

    @Override
    public List<Categoria> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Categoria findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Categoria> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }
    
    @Override
    @Transactional
    public Categoria create(Categoria categoria) {
        repository.persist(categoria);
        return categoria;
    }

    @Override
    @Transactional
    public void update(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = repository.findById(id);

        if (categoria.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                "Conflito de concorrência: a categoria foi alterado por outra transação."
            );
        }

        categoria.setTipo(dto.tipo());
        repository.persist(categoria);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
