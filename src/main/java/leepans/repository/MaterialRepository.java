package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Material;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material>{
    
    public PanacheQuery<Material> findByNome(String nome) {
        return find("SELECT m FROM Material m WHERE UPPER(m.nome) LIKE UPPER(?1)", "%" + nome + "%");
    }
}
