package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Sustentacao;

@ApplicationScoped
public class SustentacaoRepository implements PanacheRepository<Sustentacao>{
    
public PanacheQuery<Sustentacao> findByMaterial(Long idmaterial) {
    return find("select s from Sustentacao s join s.materiais m where m.id = ?1", idmaterial);
}
}
