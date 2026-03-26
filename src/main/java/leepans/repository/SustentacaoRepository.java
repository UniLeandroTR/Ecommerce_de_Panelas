package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Sustentacao;

@ApplicationScoped
public class SustentacaoRepository implements PanacheRepository<Sustentacao>{
    
}
