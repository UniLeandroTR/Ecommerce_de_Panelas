package leepans.model;

public enum StatusPagamento {
    PENDENTE(1L, "Pendente"),
    APROVADO(2L, "Aprovado"),
    RECUSADO(3L, "Recusado");

    private final Long id;
    private final String nome;

    StatusPagamento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public static StatusPagamento valueOf(Long id){
        for(StatusPagamento status : StatusPagamento.values()){
            if(status.getId().equals(id)){
                return status;
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
