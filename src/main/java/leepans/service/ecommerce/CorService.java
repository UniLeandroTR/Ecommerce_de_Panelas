package leepans.service.ecommerce;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import leepans.dto.cor.CorRequestDTO;
import leepans.model.Cor;
import leepans.repository.CorRepository;

@ApplicationScoped
public class CorService implements CorServiceInter{

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
    public List<Cor> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    @Transactional
    public Cor create(Cor cor) {
        repository.persist(cor);
        return cor;
    }

    @Override
    @Transactional
    public void update(Long id, CorRequestDTO dto) {
        Cor cor = repository.findById(id);
        if (cor.getVersion() != dto.version()) {
            throw new OptimisticLockException(
                "Conflito de concorrência: a cor foi alterado por outra transação."
            );
        }
        if(cor != null){
            cor.setNome(dto.nome());
            repository.persist(cor);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
