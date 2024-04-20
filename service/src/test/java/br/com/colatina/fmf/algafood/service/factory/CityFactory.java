package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.City;
import br.com.colatina.fmf.algafood.service.domain.repository.CityRepository;
import br.com.colatina.fmf.algafood.service.domain.service.CityCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityFactory extends BaseEntityFactory<City> {
	@Autowired
	CityMapper cityMapper;
	@Autowired
	CityCrudService cityCrudService;
	@Autowired
	CityRepository cityRepository;

	@Autowired
	StateFactory stateFactory;

	@Override
	public City createEntity() {
		City city = new City();
		city.setName(String.format("City %d", System.currentTimeMillis()));
		city.setAcronym(String.valueOf(System.currentTimeMillis()).substring(0, 5));
		city.setState(stateFactory.createAndPersist());
		return city;
	}

	@Override
	protected City persist(City entity) {
		CityDto dto = cityMapper.toDto(entity);
		dto = cityCrudService.insert(dto);
		return cityMapper.toEntity(dto);
	}

	@Override
	public City getById(Long id) {
		return cityRepository.findById(id).orElse(null);
	}
}
