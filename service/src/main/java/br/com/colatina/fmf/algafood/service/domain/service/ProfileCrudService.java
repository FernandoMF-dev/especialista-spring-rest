package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.repository.ProfileRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileCrudService {
	private final ProfileRepository profileRepository;
	private final ProfileMapper profileMapper;


	public List<ProfileDto> findAll() {
		return profileRepository.findAllDto();
	}

	public ProfileDto findDtoById(Long id) {
		return profileRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile.not_found"));
	}

	public Profile findEntityById(Long id) {
		return profileRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile.not_found"));
	}

	public ProfileDto insert(ProfileDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public ProfileDto update(ProfileDto dto, @PathVariable Long id) {
		ProfileDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		Profile saved = findEntityById(id);
		saved.setExcluded(true);
		profileRepository.save(saved);
	}

	private ProfileDto save(ProfileDto dto) {
		Profile entity = profileMapper.toEntity(dto);
		entity = profileRepository.save(entity);
		return profileMapper.toDto(entity);
	}

}
