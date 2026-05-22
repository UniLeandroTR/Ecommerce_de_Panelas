package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.ListaDesejo;

@ApplicationScoped
public class ListaDesejoRepository implements PanacheRepository<ListaDesejo> {
    
    public PanacheQuery<ListaDesejo> findByUsuarioLogin(String usuarioLogin) {
        return find("usuario.login", usuarioLogin);
    }
}
