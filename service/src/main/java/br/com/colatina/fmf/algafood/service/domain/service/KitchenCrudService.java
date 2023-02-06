package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.api.model.KitchensXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.repository.KitchenRepository;
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

	public List<Kitchen> findAll() {
		return kitchenRepository.findAll();
	}

	public KitchensXmlWrapper findAllXml() {
		return new KitchensXmlWrapper(findAll());
	}

	public Optional<Kitchen> findById(Long id) {
		return kitchenRepository.findByIdAndExcludedIsFalse(id);
	}

	public Kitchen insert(Kitchen entity) {
		return kitchenRepository.save(entity);
	}

	public Kitchen update(Kitchen entity, @PathVariable Long id) {
		Optional<Kitchen> saved = kitchenRepository.findByIdAndExcludedIsFalse(id);
		if (saved.isEmpty()) {
			return null;
		}
		BeanUtils.copyProperties(entity, saved.get(), "id");
		return kitchenRepository.save(saved.get());
	}

	public boolean delete(Long id) {
		Optional<Kitchen> saved = kitchenRepository.findByIdAndExcludedIsFalse(id);
		if (saved.isEmpty()) {
			return false;
		}
		saved.get().setExcluded(true);
		kitchenRepository.save(saved.get());
		return true;
	}
}
