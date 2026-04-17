package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.Panela;

@ApplicationScoped
public class PanelaRepository implements PanacheRepository<Panela>{
    
    @Override
    public PanacheQuery<Panela> findAll(){
        return find("SELECT DISTINCT p FROM Panela p "+
            "LEFT JOIN FETCH p.categoria "+
            "LEFT JOIN FETCH p.fornecedor "+
            "LEFT JOIN FETCH p.tampa t "+
            "LEFT JOIN FETCH t.cor "+
            "LEFT JOIN FETCH p.fundo f "+
            "LEFT JOIN FETCH f.cor "+
            "LEFT JOIN FETCH p.sustentacao s "+
            "LEFT JOIN FETCH s.cor");
    }

    public PanacheQuery<Panela> findByCategoria(Long idcategoria) {
        return find("categoria.id", idcategoria);
    }

    public PanacheQuery<Panela> findByColecao(Long idcolecao) {
        return find("colecao.id", idcolecao);
    }

}
