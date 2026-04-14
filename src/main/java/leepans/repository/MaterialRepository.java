package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Material;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material>{
    
    @Override
    public PanacheQuery<Material> findAll(){
        return find("SELECT m FROM Material LEFT JOIN FETCH m.qualidades");
    }
}
