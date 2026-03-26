package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Fornecedor;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor>{
    
}
