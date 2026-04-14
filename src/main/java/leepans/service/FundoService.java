package leepans.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import leepans.dto.fundo.FundoRequestDTO;
import leepans.model.Fundo;
import leepans.repository.CorRepository;
import leepans.repository.FundoRepository;
import leepans.repository.MaterialRepository;

import java.util.List;

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
    public Fundo create(Fundo fundo) {
        repository.persist(fundo);
        return fundo;
    }

    @Override
    public void update(Long id, FundoRequestDTO dto) {
        Fundo fundo = findById(id);

        if(fundo != null){
            fundo.setCor(corRepository.findById(dto.idCor()));
            fundo.setMaterial(materialRepository.findById(dto.idMaterial()));
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
