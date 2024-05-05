package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.service.dto.GenericObjectDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper extends EntityMapper<PaymentMethodDto, PaymentMethod> {
	@Mapping(source = "description", target = "name")
	GenericObjectDto toGenericDto(PaymentMethod entity);
}
