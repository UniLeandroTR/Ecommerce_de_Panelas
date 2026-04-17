package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Material;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material>{
    

}
