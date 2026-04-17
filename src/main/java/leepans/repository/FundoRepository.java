package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Fundo;

@ApplicationScoped
public class FundoRepository implements PanacheRepository<Fundo>{
    
    public PanacheQuery<Fundo> findByCor(Long idcor) {
        return find("cor.id", idcor);
    }
}
