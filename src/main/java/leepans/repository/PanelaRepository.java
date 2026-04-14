package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Panela;

@ApplicationScoped
public class PanelaRepository implements PanacheRepository<Panela>{
    
    @Override
    public PanacheQuery<Panela> findAll(){
        return find("SELECT p FROM Panela "+
            "LEFT JOIN FETCH p.categoria "+
            "LEFT JOIN FETCH p.fornecedor "+
            "LEFT JOIN FETCH p.tampa "+
            "LEFT JOIN FETCH p.fundo "+
            "LEFT JOIN FETCH p.sustentacao");
    }
}
