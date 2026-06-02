package leepans.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import leepans.model.TipoPagamento;

@Converter(autoApply = true)
public class TipoPagamentoConverter implements AttributeConverter<TipoPagamento, Long>{

    @Override
    public Long convertToDatabaseColumn(TipoPagamento tipo) {
        return tipo==null ? null : tipo.getID();
    }

    @Override
    public TipoPagamento convertToEntityAttribute(Long id) {
        return TipoPagamento.valueOf(id);
    }
    
}
