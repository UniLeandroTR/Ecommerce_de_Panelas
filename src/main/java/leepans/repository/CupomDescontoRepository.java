package leepans.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import leepans.model.CupomDesconto;

@ApplicationScoped
public class CupomDescontoRepository implements PanacheRepository<CupomDesconto> {

    public PanacheQuery<CupomDesconto> findByAtivo(boolean ativo) {
        return find("ativo", ativo);
    }

    public PanacheQuery<CupomDesconto> findByCodigo(String codigo) {
        return find("SELECT c FROM CupomDesconto c WHERE UPPER(c.codigo) LIKE UPPER(?1)", "%" + codigo + "%");
    }

    public PanacheQuery<CupomDesconto> findByAtivoAndValorMinimoCompra(boolean ativo, Double valorMinimo) {
        return find("ativo = ?1 and valorMinimoCompra <= ?2", ativo, valorMinimo);
    }
}
