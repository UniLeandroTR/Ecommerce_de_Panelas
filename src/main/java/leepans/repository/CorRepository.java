package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Cor;

@ApplicationScoped
public class CorRepository implements PanacheRepository<Cor>{
    
}
