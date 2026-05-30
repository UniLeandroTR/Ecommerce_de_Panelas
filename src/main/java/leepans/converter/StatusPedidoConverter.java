package leepans.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import leepans.model.StatusPedido;

@Converter(autoApply = true)
public class StatusPedidoConverter implements AttributeConverter<StatusPedido, Long>{

    @Override
    public Long convertToDatabaseColumn(StatusPedido status) {
        return status==null ? null : status.getId();
    }

    @Override
    public StatusPedido convertToEntityAttribute(Long id) {
        return StatusPedido.valueOf(id);
    }
    
}
