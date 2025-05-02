package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.repository.ProfileRepository;
import br.com.colatina.fmf.algafood.service.domain.service.ProfileCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProfileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProfileFactory extends BaseEntityFactory<Profile> {
	@Autowired
	ProfileCrudService profileCrudService;
	@Autowired
	ProfileRepository profileRepository;
	@Autowired
	ProfileMapper profileMapper;

	@Override
	public Profile createEntity() {
		Profile profile = new Profile();
		profile.setName(String.format("Profile %d", System.currentTimeMillis()));
		return profile;
	}

	@Override
	public Profile persist(Profile entity) {
		ProfileDto dto = profileMapper.toDto(entity);
		dto = profileCrudService.insert(dto);
		return profileMapper.toEntity(dto);
	}

	@Override
	public Profile getById(Long id) {
		return profileRepository.findById(id).orElse(null);
	}
}
