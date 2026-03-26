package leepans.model;

import jakarta.persistence.Entity;

@Entity
public class Tampa extends Componente {
    private Boolean isDePressao;

    public Boolean getIsDePressao() {
        return isDePressao;
    }

    public void setIsDePressao(Boolean isDePressao) {
        this.isDePressao = isDePressao;
    }
}