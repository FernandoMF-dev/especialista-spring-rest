package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Permission;
import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.repository.ProfileRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProfileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileCrudService {
	private final ProfileRepository profileRepository;
	private final ProfileMapper profileMapper;

	private final PermissionCrudService permissionCrudService;

	public List<ProfileDto> findAll() {
		return profileRepository.findAllDto();
	}

	public ProfileDto findDtoById(Long id) {
		return profileRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile.not_found", id));
	}

	public Profile findEntityById(Long id) {
		return profileRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("profile.not_found", id));
	}

	public ProfileDto insert(ProfileDto dto) {
		dto.setId(null);
		Profile entity = profileMapper.toEntity(dto);
		return save(entity);
	}

	public ProfileDto update(ProfileDto dto, Long id) {
		Profile saved = findEntityById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		Profile saved = findEntityById(id);
		saved.setExcluded(true);
		profileRepository.save(saved);
	}

	public Set<ProfileDto> findAllDtoByUser(Long userId) {
		return profileRepository.findAllDtoByUser(userId);
	}

	public Set<PermissionDto> findAllPermissionsByProfile(Long profileId) {
		findEntityById(profileId);
		return permissionCrudService.findAllDtoByProfile(profileId);
	}

	public void addPermissionToProfile(Long profileId, Long permissionId) {
		Profile profile = findEntityById(profileId);
		Permission permission = permissionCrudService.findEntityById(permissionId);
		profile.addPermission(permission);
		profileRepository.save(profile);
	}

	public void removePermissionFromProfile(Long profileId, Long permissionId) {
		Profile profile = findEntityById(profileId);
		Permission permission = permissionCrudService.findEntityById(permissionId);
		profile.removePermission(permission);
		profileRepository.save(profile);
	}

	private ProfileDto save(Profile entity) {
		entity = profileRepository.save(entity);
		return profileMapper.toDto(entity);
	}

}
