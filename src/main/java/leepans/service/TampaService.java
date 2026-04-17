package leepans.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.tampa.TampaRequestDTO;
import leepans.model.Tampa;
import leepans.repository.CorRepository;
import leepans.repository.MaterialRepository;
import leepans.repository.TampaRepository;

@ApplicationScoped
public class TampaService implements TampaServiceInter {

    @Inject
    TampaRepository repository;

    @Inject
    CorRepository corRepository;

    @Inject
    MaterialRepository materialRepository;

    @Override
    public List<Tampa> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Tampa findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Tampa create(Tampa tampa) {
        repository.persist(tampa);
        if (tampa.getMateriais() != null) {
            tampa.getMateriais().forEach(material -> Hibernate.initialize(material.getQualidades()));
        }
        return tampa;
    }

    @Override
    public void update(Long id, TampaRequestDTO dto) {
        Tampa tampa = findById(id);

        if(tampa!= null){
            tampa.setCor(corRepository.findById(dto.idCor()));
            tampa.setMateriais(dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                    .map(materialRepository::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
            tampa.setIsDePressao(dto.isDePressao());
            tampa.setPeso(dto.peso());
            repository.persist(tampa);
        }
    }

    @Override
    public void delete(Long id) {
        Tampa tampa = findById(id);
        if(tampa != null) repository.delete(tampa);
    }
}
