package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Colecao;

@ApplicationScoped
public class ColecaoRepository implements PanacheRepository<Colecao>{
    
    public PanacheQuery<Colecao> findByNome(String nome) {
        return find("SELECT c FROM Colecao c WHERE UPPER(c.nome) LIKE UPPER(?1)", "%" + nome + "%");
    }
}
