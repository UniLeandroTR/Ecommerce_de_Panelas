package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Fundo;

@ApplicationScoped
public class FundoRepository implements PanacheRepository<Fundo>{
    
}
