package leepans.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Material extends DefaultEntity {

    private String nome;

    @ElementCollection
    private List<String> qualidades;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getQualidades() {
        return qualidades;
    }

    public void setQualidades(List<String> qualidades) {
        this.qualidades = qualidades;
    }
}