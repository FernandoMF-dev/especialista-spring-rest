package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.api.model.KitchensXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceInUse;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFound;
import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.repository.KitchenRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.KitchenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KitchenCrudService {
	private final KitchenRepository kitchenRepository;
	private final KitchenMapper kitchenMapper;

	public List<KitchenDto> findAll() {
		return kitchenRepository.findAllDto();
	}

	public KitchensXmlWrapper findAllXml() {
		return new KitchensXmlWrapper(findAll());
	}

	public KitchenDto findDtoById(Long id) {
		return kitchenRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFound(String.format("Kitchen %d not found", id)));
	}

	public Kitchen findEntityById(Long id) {
		return kitchenRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFound(String.format("Kitchen %d not found", id)));
	}

	public KitchenDto findFirst() {
		Optional<Kitchen> entity = kitchenRepository.findFirst();

		if (entity.isEmpty()) {
			throw new ResourceNotFound("No kitchen found");
		}
		return kitchenMapper.toDto(entity.get());
	}

	public KitchenDto insert(KitchenDto dto) {
		dto.setId(null);
		return save(dto);
	}

	public KitchenDto update(KitchenDto dto, @PathVariable Long id) {
		KitchenDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id");
		return save(saved);
	}

	public void delete(Long id) {
		validateDelete(id);

		Kitchen saved = findEntityById(id);
		saved.setExcluded(true);
		kitchenRepository.save(saved);
	}

	private KitchenDto save(KitchenDto dto) {
		Kitchen entity = kitchenMapper.toEntity(dto);
		entity = kitchenRepository.save(entity);
		return kitchenMapper.toDto(entity);
	}

	private void validateDelete(Long id) {
		if (kitchenRepository.isKitchenInUse(id)) {
			throw new ResourceInUse(String.format("Kitchen %d is currently being used by another resource and cannot be deleted", id));
		}
	}
}
