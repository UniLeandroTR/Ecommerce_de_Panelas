package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Panela;

@ApplicationScoped
public class PanelaRepository implements PanacheRepository<Panela>{
    
}
