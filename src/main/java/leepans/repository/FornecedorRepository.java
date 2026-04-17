package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Fornecedor;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor>{
    
    public PanacheQuery<Fornecedor> findByNome(String nome) {
        return find("SELECT f FROM Fornecedor f WHERE UPPER(f.nome) LIKE UPPER(?1)", "%" + nome + "%");
    }
}
