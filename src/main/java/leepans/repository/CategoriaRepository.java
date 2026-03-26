package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Categoria;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria>{
    
}
