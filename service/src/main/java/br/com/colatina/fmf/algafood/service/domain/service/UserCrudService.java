package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.User;
import br.com.colatina.fmf.algafood.service.domain.repository.UserRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

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
				.orElseThrow(() -> new ResourceNotFoundException(String.format("User %d not found", id)));
	}

	public User findEntityById(Long id) {
		return userRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("User %d not found", id)));
	}

	public UserDto insert(UserDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public UserDto update(UserDto dto, @PathVariable Long id) {
		UserDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		User saved = findEntityById(id);
		saved.setExcluded(true);
		userRepository.save(saved);
	}

	private UserDto save(UserDto dto) {
		User entity = userMapper.toEntity(dto);
		entity = userRepository.save(entity);
		return userMapper.toDto(entity);
	}
}
