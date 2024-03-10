package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFound;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantMapper;
import br.com.colatina.fmf.algafood.service.infrastructure.specification.RestaurantSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantCrudService {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantMapper restaurantMapper;
	private final RestaurantSpecs restaurantSpecs;

	private final KitchenCrudService kitchenCrudService;

	public List<RestaurantDto> findAll() {
		return restaurantRepository.findAllDto();
	}

	public RestaurantDto findDtoById(Long id) {
		return restaurantRepository.findDtoById(id)
				.orElseThrow(() -> new ResourceNotFound(String.format("Restaurant %d not found", id)));
	}

	public Restaurant findEntityById(Long id) {
		return restaurantRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFound(String.format("Restaurant %d not found", id)));
	}

	public List<RestaurantDto> filterByFreightRate(Double min, Double max) {
		return restaurantRepository.filterDtoByFreightRate(min, max);
	}

	public List<RestaurantDto> filterByFreightRate(String name, Double min, Double max) {
		return restaurantRepository.filterEntityByFreightRate(name, min, max).stream()
				.map(restaurantMapper::toDto)
				.collect(Collectors.toList());
	}

	public Page<RestaurantDto> page(RestaurantPageFilter filter, Pageable pageable) {
		Specification<Restaurant> spec = restaurantSpecs.composedAnd(
				restaurantSpecs.byName(filter.getName()),
				restaurantSpecs.byMinFreightRate(filter.getMinFreightRate()),
				restaurantSpecs.byMaxFreightRate(filter.getMaxFreightRate()),
				restaurantSpecs.byActive(filter.getActive()),
				restaurantSpecs.byKitchenId(filter.getKitchenId()),
				restaurantSpecs.byExcluded(Boolean.FALSE)
		);

		return restaurantRepository.findAll(spec, pageable).map(restaurantMapper::toDto);
	}

	public RestaurantDto findFirst() {
		Optional<Restaurant> entity = restaurantRepository.findFirst();

		if (entity.isEmpty()) {
			throw new ResourceNotFound("No restaurant found");
		}
		return restaurantMapper.toDto(entity.get());
	}

	public RestaurantDto insert(RestaurantDto dto) {
		dto.setId(null);
		dto.setActive(Boolean.TRUE);
		dto.setRegistrationDate(LocalDateTime.now());
		return save(dto);
	}

	public RestaurantDto update(RestaurantDto dto, @PathVariable Long id) {
		RestaurantDto saved = findDtoById(id);
		BeanUtils.copyProperties(dto, saved, "id", "registrationDate", "updateDate");
		return save(saved);
	}

	public void delete(Long id) {
		Restaurant saved = findEntityById(id);
		saved.setExcluded(true);
		restaurantRepository.save(saved);
	}

	private RestaurantDto save(RestaurantDto dto) {
		dto.setUpdateDate(LocalDateTime.now());
		validateSave(dto);

		Restaurant entity = restaurantMapper.toEntity(dto);
		entity = restaurantRepository.save(entity);
		return restaurantMapper.toDto(entity);
	}

	private void validateSave(RestaurantDto dto) {
		try {
			kitchenCrudService.findEntityById(dto.getKitchenId());
		} catch (ResourceNotFound e) {
			throw new ResourceNotFound(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
