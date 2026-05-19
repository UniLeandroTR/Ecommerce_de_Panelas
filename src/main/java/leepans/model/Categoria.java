package leepans.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Categoria extends DefaultEntity {

    private String tipo;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Panela> panelas;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Panela> getPanelas() {
        return panelas;
    }

    public void setPanelas(List<Panela> panelas) {
        this.panelas = panelas;
    }
}