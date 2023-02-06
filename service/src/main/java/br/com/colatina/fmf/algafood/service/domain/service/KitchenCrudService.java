package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.api.model.KitchensXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceInUse;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFound;
import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.repository.KitchenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KitchenCrudService {
	private final KitchenRepository kitchenRepository;

	public List<Kitchen> findAll() {
		return kitchenRepository.findAll();
	}

	public KitchensXmlWrapper findAllXml() {
		return new KitchensXmlWrapper(findAll());
	}

	private static void validateDelete(Long id, boolean isKitchenInUse) {
		if (isKitchenInUse) {
			throw new ResourceInUse(String.format("Kitchen %d is currently being used by another resource and cannot be deleted", id));
		}
	}

	public Kitchen insert(Kitchen entity) {
		return kitchenRepository.save(entity);
	}

	public Kitchen findById(Long id) {
		return kitchenRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFound(String.format("Kitchen %d not found", id)));
	}

	public Kitchen update(Kitchen entity, @PathVariable Long id) {
		Kitchen saved = findById(id);
		BeanUtils.copyProperties(entity, saved, "id");
		return kitchenRepository.save(saved);
	}

	public void delete(Long id) {
		Kitchen saved = findById(id);
		boolean isKitchenInUse = kitchenRepository.isKitchenInUse(id);

		validateDelete(id, isKitchenInUse);

		saved.setExcluded(true);
		kitchenRepository.save(saved);
	}
}
