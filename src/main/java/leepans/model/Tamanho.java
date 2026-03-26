package leepans.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Tamanho {
    PEQUENA(1L, "Pequena"),
    MEDIA(2L, "Média"),
    GRANDE(3L, "Grande"),
    GIGANTE(4L, "Gigante");

    private final Long id;
    private final String nome;
    
    Tamanho(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Tamanho valueOf(Long id){
        for(Tamanho tamanho : Tamanho.values()){
            if(tamanho.getId().equals(id)){
                return tamanho;
            }
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}