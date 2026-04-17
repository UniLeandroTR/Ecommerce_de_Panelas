package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Categoria;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria>{
    
    public PanacheQuery<Categoria> findByNome(String nome) {
        return find("SELECT c FROM Categoria c WHERE UPPER(c.tipo) LIKE UPPER(?1)", "%" + nome + "%");
    }
}
