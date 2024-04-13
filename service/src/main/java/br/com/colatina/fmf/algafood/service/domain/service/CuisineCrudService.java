package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.api.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceInUseException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.repository.CuisineRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CuisineCrudService {
	private final CuisineRepository cuisineRepository;
	private final CuisineMapper cuisineMapper;

	public List<CuisineDto> findAll() {
		return cuisineRepository.findAllDto();
	}

	public CuisinesXmlWrapper findAllXml() {
		return new CuisinesXmlWrapper(findAll());
	}

	public CuisineDto findDtoById(Long id) {
		return cuisineRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFoundException("cuisine.not_found"));
	}

	public Cuisine findEntityById(Long id) {
		return cuisineRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("cuisine.not_found"));
	}

	public CuisineDto findFirst() {
		Optional<Cuisine> entity = cuisineRepository.findFirst();

		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("cuisine.none_found");
		}
		return cuisineMapper.toDto(entity.get());
	}

	public CuisineDto insert(CuisineDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public CuisineDto update(CuisineDto dto, @PathVariable Long id) {
		CuisineDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		validateDelete(id);

		Cuisine saved = findEntityById(id);
		saved.setExcluded(true);
		cuisineRepository.save(saved);
	}

	private CuisineDto save(CuisineDto dto) {
		Cuisine entity = cuisineMapper.toEntity(dto);
		entity = cuisineRepository.save(entity);
		return cuisineMapper.toDto(entity);
	}

	private void validateDelete(Long id) {
		if (cuisineRepository.isCuisineInUse(id)) {
			throw new ResourceInUseException("cuisine.in_use");
		}
	}
}
