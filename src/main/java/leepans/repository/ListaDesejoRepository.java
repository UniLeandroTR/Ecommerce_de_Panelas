package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.ListaDesejo;

@ApplicationScoped
public class ListaDesejoRepository implements PanacheRepository<ListaDesejo> {
    
    public ListaDesejo findByUsuarioId(Long usuarioId) {
        return find("SELECT ld FROM ListaDesejo ld WHERE ld.usuario.id = ?1", usuarioId).firstResult();
    }
}
