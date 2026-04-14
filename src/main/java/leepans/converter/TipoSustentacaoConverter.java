package leepans.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import leepans.model.TipoSustentacao;

@Converter(autoApply = true)
public class TipoSustentacaoConverter implements AttributeConverter<TipoSustentacao, Long>{

    @Override
    public Long convertToDatabaseColumn(TipoSustentacao tipo) {
        return tipo == null ? null : tipo.getId();
    }

    @Override
    public TipoSustentacao convertToEntityAttribute(Long id) {
        return TipoSustentacao.valueOf(id);
    }
}
