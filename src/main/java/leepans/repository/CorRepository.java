package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Cor;

@ApplicationScoped
public class CorRepository implements PanacheRepository<Cor>{
    
    public PanacheQuery<Cor> findByNome(String nome) {
        return find("SELECT c FROM Cor c WHERE UPPER(c.nome) LIKE UPPER(?1)", "%" + nome + "%");
    }
}
