package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.DuplicateResourceException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.PasswordMismatchException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.repository.AppUserRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserCrudService {
	private final AppUserRepository appUserRepository;
	private final AppUserMapper appUserMapper;

	private final ProfileCrudService profileCrudService;

	private final PasswordEncoder passwordEncoder;

	public List<AppUserDto> findAll() {
		return appUserRepository.findAllDto();
	}

	public AppUserDto findDtoById(Long id) {
		return appUserRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user.not_found", id));
	}

	public AppUser findEntityById(Long id) {
		return appUserRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("user.not_found", id));
	}

	public AppUserDto insert(AppUserInsertDto dto) {
		dto.setId(null);
		AppUser entity = appUserMapper.toEntity(dto);
		return save(entity);
	}

	public AppUserDto update(AppUserDto dto, @PathVariable Long id) {
		AppUser saved = findEntityById(id);
		BeanUtils.copyProperties(dto, saved, "id", "password");
		return save(saved);
	}

	public void changePassword(PasswordChangeDto dto, Long userId) {
		AppUser appUser = findEntityById(userId);
		if (!passwordEncoder.matches(dto.getCurrentPassword(), appUser.getPassword())) {
			throw new PasswordMismatchException("password_change.current.mismatch");
		}
		appUser.setPassword(dto.getNewPassword());
		appUserRepository.save(appUser);
	}

	public void delete(Long id) {
		AppUser saved = findEntityById(id);
		saved.setExcluded(true);
		appUserRepository.save(saved);
	}

	public Set<ProfileDto> findAllProfilesByUser(Long userId) {
		findEntityById(userId);
		return profileCrudService.findAllDtoByUser(userId);
	}

	public void addProfileToUser(Long userId, Long profileId) {
		AppUser appUser = findEntityById(userId);
		Profile profile = profileCrudService.findEntityById(profileId);
		appUser.addProfile(profile);
		appUserRepository.save(appUser);
	}

	public void removeProfileFromUser(Long userId, Long profileId) {
		AppUser appUser = findEntityById(userId);
		Profile profile = profileCrudService.findEntityById(profileId);
		appUser.removeProfile(profile);
		appUserRepository.save(appUser);
	}

	private AppUserDto save(AppUser entity) {
		appUserRepository.detach(entity);
		validateSave(entity);
		encodePassword(entity);
		entity = appUserRepository.save(entity);
		return appUserMapper.toDto(entity);
	}

	private void validateSave(AppUser entity) {
		Optional<AppUser> existingUser = appUserRepository.findByEmailAndExcludedIsFalse(entity.getEmail());
		if (existingUser.isPresent() && !existingUser.get().equals(entity)) {
			throw new DuplicateResourceException("user.email.duplicate", entity.getEmail());
		}
	}

	private void encodePassword(AppUser entity) {
		if (entity.isNew()) {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		}
	}
}
