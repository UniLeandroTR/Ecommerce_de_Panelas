package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Tampa;

@ApplicationScoped
public class TampaRepository implements PanacheRepository<Tampa>{
    
public PanacheQuery<Tampa> findByMaterial(Long idmaterial) {
    return find("select t from Tampa t join t.materiais m where m.id = ?1", idmaterial);
}
}
