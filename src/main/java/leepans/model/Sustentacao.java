package leepans.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Sustentacao extends Componente {
    private Integer tamanhoEmCm;
    private Integer quantidade;

    @Column(name = "codigo_tipo_sustentacao")
    private TipoSustentacao tipoSustentacao;

    public Integer getTamanhoEmCm() {
        return tamanhoEmCm;
    }

    public void setTamanhoEmCm(Integer tamanhoEmCm) {
        this.tamanhoEmCm = tamanhoEmCm;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public TipoSustentacao getTipoSustentacao() {
        return tipoSustentacao;
    }

    public void setTipoSustentacao(TipoSustentacao tipoSustentacao) {
        this.tipoSustentacao = tipoSustentacao;
    }
}