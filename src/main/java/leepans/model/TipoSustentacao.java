package leepans.model;

public enum TipoSustentacao {
    CABO(1L, "Cabo"),
    ALCA(2L, "Alça");

    private final Long id;
    private final String nome;

    TipoSustentacao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public TipoSustentacao valueOf(Long id){
        for(TipoSustentacao tipo : TipoSustentacao.values()){
            if(tipo.getId().equals(id)){
                return tipo;
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