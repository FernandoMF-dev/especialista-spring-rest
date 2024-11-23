package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.GenericObjectDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDto, AppUser> {
	AppUser toEntity(AppUserInsertDto dto);

	GenericObjectDto toGenericDto(AppUser entity);
}
