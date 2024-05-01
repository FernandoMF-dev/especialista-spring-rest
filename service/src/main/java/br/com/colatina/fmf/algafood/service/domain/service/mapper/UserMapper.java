package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserInsertDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
	User toEntity(UserInsertDto dto);
}
