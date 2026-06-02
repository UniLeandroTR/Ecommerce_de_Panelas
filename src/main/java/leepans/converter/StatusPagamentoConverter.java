package leepans.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import leepans.model.StatusPagamento;

@Converter(autoApply = true)
public class StatusPagamentoConverter implements AttributeConverter<StatusPagamento, Long>{

    @Override
    public Long convertToDatabaseColumn(StatusPagamento status) {
        return status==null ? null : status.getId();
    }

    @Override
    public StatusPagamento convertToEntityAttribute(Long id) {
        return StatusPagamento.valueOf(id);
    }
    
}
