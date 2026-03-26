package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Tampa;

@ApplicationScoped
public class TampaRepository implements PanacheRepository<Tampa>{
    
}
