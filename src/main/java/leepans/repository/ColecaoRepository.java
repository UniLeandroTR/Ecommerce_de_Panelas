package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Colecao;

@ApplicationScoped
public class ColecaoRepository implements PanacheRepository<Colecao>{
    
}
