package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Endereco;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public PanacheQuery<Endereco> findByUsuario(String login) {
        return find("SELECT e FROM Endereco e "+
        "INNER JOIN Usuario u ON u.endereco.id = e.id "+
        "WHERE u.login = ?1", login);
    }

    public PanacheQuery<Endereco> findByCidade(String cidade) {
        return find("SELECT e FROM Endereco e WHERE UPPER(e.cidade) LIKE UPPER(?1)", "%" + cidade + "%");
    }

    public PanacheQuery<Endereco> findByEstado(String estado) {
        return find("SELECT e FROM Endereco e WHERE UPPER(e.estado) LIKE UPPER(?1)", "%" + estado + "%");
    }

    public Endereco findByAllFields(String rua, String numero, String cidade, String estado, String cep) {
        return find("rua = ?1 AND numero = ?2 AND UPPER(cidade) = UPPER(?3) AND UPPER(estado) = UPPER(?4) AND cep = ?5",
                rua, numero, cidade, estado, cep).firstResult();
    }
}
