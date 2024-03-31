package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.State;
import br.com.colatina.fmf.algafood.service.domain.repository.StateRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.StateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StateCrudService {
	private final StateRepository stateRepository;
	private final StateMapper stateMapper;

	public List<StateDto> findAll() {
		return stateRepository.findAllDto();
	}

	public StateDto findDtoById(Long id) {
		return stateRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("State %d not found", id)));
	}

	public State findEntityById(Long id) {
		return stateRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("State %d not found", id)));
	}

	public StateDto insert(StateDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public StateDto update(StateDto dto, @PathVariable Long id) {
		StateDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		State saved = findEntityById(id);
		saved.setExcluded(true);
		stateRepository.save(saved);
	}

	private StateDto save(StateDto dto) {
		State entity = stateMapper.toEntity(dto);
		entity = stateRepository.save(entity);
		return stateMapper.toDto(entity);
	}
}
