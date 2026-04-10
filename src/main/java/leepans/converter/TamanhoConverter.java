package leepans.converter;

import jakarta.persistence.AttributeConverter;
import leepans.model.Tamanho;

public class TamanhoConverter implements AttributeConverter<Tamanho, Long>{

    @Override
    public Long convertToDatabaseColumn(Tamanho tamanho) {
        return tamanho==null ? null : tamanho.getId();
    }

    @Override
    public Tamanho convertToEntityAttribute(Long id) {
        return Tamanho.valueOf(id);
    }
    
}
