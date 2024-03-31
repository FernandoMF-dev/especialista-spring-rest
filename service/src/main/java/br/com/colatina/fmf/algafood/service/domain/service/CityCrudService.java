package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.City;
import br.com.colatina.fmf.algafood.service.domain.repository.CityRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CityCrudService {
	private final CityRepository cityRepository;
	private final CityMapper cityMapper;

	private final StateCrudService stateCrudService;

	public List<CityDto> findAll() {
		return cityRepository.findAllDto();
	}

	public CityDto findDtoById(Long id) {
		return cityRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("City %d not found", id)));
	}

	public City findEntityById(Long id) {
		return cityRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("City %d not found", id)));
	}

	public CityDto insert(CityDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public CityDto update(CityDto dto, @PathVariable Long id) {
		CityDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id", "registrationDate", "updateDate");
		return save(saved);
	}

	public void delete(Long id) {
		City saved = findEntityById(id);
		saved.setExcluded(true);
		cityRepository.save(saved);
	}

	private CityDto save(CityDto dto) {
		validateSave(dto);

		City entity = cityMapper.toEntity(dto);
		entity = cityRepository.save(entity);
		return cityMapper.toDto(entity);
	}

	private void validateSave(CityDto dto) {
		try {
			stateCrudService.findEntityById(dto.getState().getId());
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
