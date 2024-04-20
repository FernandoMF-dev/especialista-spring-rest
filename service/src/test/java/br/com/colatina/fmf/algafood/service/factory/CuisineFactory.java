package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.repository.CuisineRepository;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CuisineFactory extends BaseEntityFactory<Cuisine> {
	@Autowired
	CuisineMapper cuisineMapper;
	@Autowired
	CuisineCrudService cuisineCrudService;
	@Autowired
	CuisineRepository cuisineRepository;

	@Override
	public Cuisine createEntity() {
		Cuisine cuisine = new Cuisine();
		cuisine.setName(String.format("Cuisine %d", System.currentTimeMillis()));
		return cuisine;
	}

	@Override
	protected Cuisine persist(Cuisine entity) {
		CuisineDto dto = cuisineMapper.toDto(entity);
		dto = cuisineCrudService.insert(dto);
		return cuisineMapper.toEntity(dto);
	}

	@Override
	public Cuisine getById(Long id) {
		return cuisineRepository.findById(id).orElse(null);
	}
}
