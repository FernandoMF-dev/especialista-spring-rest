package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.PasswordMismatchException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.repository.UserRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCrudService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public List<UserDto> findAll() {
		return userRepository.findAllDto();
	}

	public UserDto findDtoById(Long id) {
		return userRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user.not_found"));
	}

	public User findEntityById(Long id) {
		return userRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("user.not_found"));
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
		if (!Objects.equals(user.getPassword(), dto.getCurrentPassword())) {
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

	private UserDto save(User entity) {
		entity = userRepository.save(entity);
		return userMapper.toDto(entity);
	}
}
