package leepans.model;

import jakarta.persistence.Entity;

@Entity
public class Fundo extends Componente {
    private Double espessura;
    private Boolean isAntiaderente;

    public Double getEspessura() {
        return espessura;
    }

    public void setEspessura(Double espessura) {
        this.espessura = espessura;
    }

    public Boolean getIsAntiaderente() {
        return isAntiaderente;
    }

    public void setIsAntiaderente(Boolean isAntiaderente) {
        this.isAntiaderente = isAntiaderente;
    }
}