package leepans.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import leepans.model.Tamanho;

@Converter(autoApply = true)
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
