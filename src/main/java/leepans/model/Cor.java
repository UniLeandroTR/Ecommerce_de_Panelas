package leepans.model;

import jakarta.persistence.Entity;

@Entity
public class Cor extends DefaultEntity {

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}