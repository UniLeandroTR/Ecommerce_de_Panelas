package leepans.service;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.CorRequestDTO;
import leepans.model.Cor;
import leepans.repository.CorRepository;

@ApplicationScoped
public class CorServiceImpl implements CorService{

    @Inject
    CorRepository repository;

    @Override
    public List<Cor> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Cor findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Cor create(Cor cor) {
        repository.persist(cor);
        return cor;
    }

    @Override
    public void update(Long id, CorRequestDTO dto) {
        Cor cor = repository.findById(id);
        if(cor != null){
            cor.setNome(dto.nome());
            repository.persist(cor);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
