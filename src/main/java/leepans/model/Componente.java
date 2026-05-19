package leepans.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Componente extends DefaultEntity {

    private Double peso;

    @ManyToMany
    @JoinTable(
        name = "componente_material",
        joinColumns = @JoinColumn(name = "componente_id"),
        inverseJoinColumns = @JoinColumn(name = "material_id")
    )
    private List<Material> materiais = new ArrayList<>();

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }
}