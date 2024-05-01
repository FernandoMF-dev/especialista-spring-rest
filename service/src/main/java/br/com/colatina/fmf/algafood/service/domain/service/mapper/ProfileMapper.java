package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDto, Profile> {
}
