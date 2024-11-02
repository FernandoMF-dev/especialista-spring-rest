package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.DuplicateResourceException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.PasswordMismatchException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Profile;
import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.repository.UserRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.UserMapper;
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
public class UserCrudService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	private final ProfileCrudService profileCrudService;

	private final PasswordEncoder passwordEncoder;

	public List<UserDto> findAll() {
		return userRepository.findAllDto();
	}

	public UserDto findDtoById(Long id) {
		return userRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user.not_found", id));
	}

	public User findEntityById(Long id) {
		return userRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("user.not_found", id));
	}

	public UserDto insert(UserInsertDto dto) {
		dto.setId(null);
		User entity = userMapper.toEntity(dto);
		return save(entity);
	}

	public UserDto update(UserDto dto, @PathVariable Long id) {
		User saved = findEntityById(id);
		BeanUtils.copyProperties(dto, saved, "id", "password");
		return save(saved);
	}

	public void changePassword(PasswordChangeDto dto, Long userId) {
		User user = findEntityById(userId);
		if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
			throw new PasswordMismatchException("password_change.current.mismatch");
		}
		user.setPassword(dto.getNewPassword());
		userRepository.save(user);
	}

	public void delete(Long id) {
		User saved = findEntityById(id);
		saved.setExcluded(true);
		userRepository.save(saved);
	}

	public Set<ProfileDto> findAllProfilesByUser(Long userId) {
		findEntityById(userId);
		return profileCrudService.findAllDtoByUser(userId);
	}

	public void addProfileToUser(Long userId, Long profileId) {
		User user = findEntityById(userId);
		Profile profile = profileCrudService.findEntityById(profileId);
		user.addProfile(profile);
		userRepository.save(user);
	}

	public void removeProfileFromUser(Long userId, Long profileId) {
		User user = findEntityById(userId);
		Profile profile = profileCrudService.findEntityById(profileId);
		user.removeProfile(profile);
		userRepository.save(user);
	}

	private UserDto save(User entity) {
		userRepository.detach(entity);
		validateSave(entity);
		encodePassword(entity);
		entity = userRepository.save(entity);
		return userMapper.toDto(entity);
	}

	private void validateSave(User entity) {
		Optional<User> existingUser = userRepository.findByEmailAndExcludedIsFalse(entity.getEmail());
		if (existingUser.isPresent() && !existingUser.get().equals(entity)) {
			throw new DuplicateResourceException("user.email.duplicate", entity.getEmail());
		}
	}

	private void encodePassword(User entity) {
		if (entity.isNew()) {
			entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		}
	}
}
