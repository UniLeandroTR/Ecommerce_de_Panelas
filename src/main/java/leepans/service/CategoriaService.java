package leepans.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.CategoriaRequestDTO;
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
    public Categoria create(Categoria categoria) {
        repository.persist(categoria);
        return categoria;
    }

    @Override
    public void update(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = repository.findById(id);
        categoria.setTipo(dto.tipo());
        repository.persist(categoria);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
