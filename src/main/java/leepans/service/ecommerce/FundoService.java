package leepans.service.ecommerce;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.fundo.FundoRequestDTO;
import leepans.model.Fundo;
import leepans.repository.CorRepository;
import leepans.repository.FundoRepository;
import leepans.repository.MaterialRepository;

@ApplicationScoped
public class FundoService implements FundoServiceInter{

    @Inject
    FundoRepository repository;

    @Inject
    CorRepository corRepository;

    @Inject
    MaterialRepository materialRepository;

    @Override
    public List<Fundo> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Fundo findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Fundo> findByCor(Long idCor) {
        return repository.findByCor(idCor).list();
    }

    @Override
    public Fundo create(Fundo fundo) {
        repository.persist(fundo);
        if (fundo.getMateriais() != null) {
            fundo.getMateriais().forEach(material -> Hibernate.initialize(material.getQualidades()));
        }
        return fundo;
    }

    @Override
    public void update(Long id, FundoRequestDTO dto) {
        Fundo fundo = findById(id);

        if(fundo != null){
            fundo.setCor(corRepository.findById(dto.idCor()));
            fundo.setMateriais(dto.idsMateriais() == null ? null : dto.idsMateriais().stream()
                    .map(materialRepository::findById)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
            fundo.setEspessura(dto.espessura());
            fundo.setPeso(dto.peso());
            fundo.setIsAntiaderente(dto.isAntiaderente());
            repository.persist(fundo);
        }
    }

    @Override
    public void delete(Long id) {
        Fundo fundo = repository.findById(id);
        if(fundo != null) repository.delete(fundo);
    }
}
