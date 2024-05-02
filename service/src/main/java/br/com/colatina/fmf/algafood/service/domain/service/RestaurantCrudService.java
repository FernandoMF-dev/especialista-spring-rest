package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.PaymentMethod;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantFormMapper;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantMapper;
import br.com.colatina.fmf.algafood.service.infrastructure.specification.RestaurantSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantCrudService {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantMapper restaurantMapper;
	private final RestaurantFormMapper restaurantFormMapper;
	private final RestaurantSpecs restaurantSpecs;

	private final CuisineCrudService cuisineCrudService;
	private final PaymentMethodCrudService paymentMethodCrudService;

	public List<RestaurantListDto> findAll() {
		return restaurantRepository.findAllDto();
	}

	public RestaurantDto findDtoById(Long id) {
		return restaurantMapper.toDto(findEntityById(id));
	}

	public Restaurant findEntityById(Long id) {
		return restaurantRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("restaurant.not_found"));
	}

	public List<RestaurantListDto> filterByFreightFee(Double min, Double max) {
		return restaurantRepository.filterDtoByFreightFee(min, max);
	}

	public List<RestaurantListDto> filterByFreightFee(String name, Double min, Double max) {
		return restaurantRepository.filterEntityByFreightFee(name, min, max).stream()
				.map(restaurantMapper::toListDto)
				.collect(Collectors.toList());
	}

	public Page<RestaurantListDto> page(RestaurantPageFilter filter, Pageable pageable) {
		Specification<Restaurant> spec = restaurantSpecs.composedAnd(
				restaurantSpecs.byName(filter.getName()),
				restaurantSpecs.byMinFreightFee(filter.getMinFreightFee()),
				restaurantSpecs.byMaxFreightFee(filter.getMaxFreightFee()),
				restaurantSpecs.byActive(filter.getActive()),
				restaurantSpecs.byCuisineId(filter.getCuisineId()),
				restaurantSpecs.byExcluded(Boolean.FALSE)
		);

		return restaurantRepository.findAll(spec, pageable).map(restaurantMapper::toListDto);
	}

	public RestaurantDto findFirst() {
		Optional<Restaurant> entity = restaurantRepository.findFirst();

		if (entity.isEmpty()) {
			throw new ResourceNotFoundException("restaurant.none_found");
		}
		return restaurantMapper.toDto(entity.get());
	}

	public RestaurantDto insert(RestaurantFormDto dto) {
		dto.setId(null);

		Restaurant entity = restaurantFormMapper.toEntity(dto);

		return save(entity);
	}

	public RestaurantDto update(RestaurantFormDto dto, @PathVariable Long id) {
		dto.setId(id);

		Restaurant saved = findEntityById(id);
		Restaurant entity = restaurantFormMapper.toEntity(dto);
		BeanUtils.copyProperties(entity, saved, "id", "registrationDate", "updateDate", "active", "products");

		return save(saved);
	}

	public void toggleActive(Long restaurantId, Boolean active) {
		Restaurant restaurant = findEntityById(restaurantId);
		restaurant.setActive(active);
		/*
		 It is not necessary to persist the 'restaurant' object with the 'save()' method.
		 The JPA persistence context is still managing this instance of 'Restaurant' and
		 will synchronize the changes with the database at the end of the transaction.

		 PS: I will continue to use the save method for other operations,
		 because I believe it makes the interaction more obvious.
		*/
	}

	public void toggleOpen(Long restaurantId, Boolean open) {
		Restaurant restaurant = findEntityById(restaurantId);
		restaurant.setOpen(open);
		/*
		 It is not necessary to persist the 'restaurant' object with the 'save()' method.
		 The JPA persistence context is still managing this instance of 'Restaurant' and
		 will synchronize the changes with the database at the end of the transaction.

		 PS: I will continue to use the save method for other operations,
		 because I believe it makes the interaction more obvious.
		*/
	}

	public void delete(Long id) {
		Restaurant saved = findEntityById(id);
		saved.setExcluded(true);
		restaurantRepository.save(saved);
	}

	private RestaurantDto save(Restaurant entity) {
		validateSave(entity);
		entity = restaurantRepository.save(entity);
		return restaurantMapper.toDto(entity);
	}

	private void validateSave(Restaurant entity) {
		try {
			cuisineCrudService.findEntityById(entity.getCuisine().getId());
			paymentMethodCrudService.verifyExistence(entity.getPaymentMethods().stream().map(PaymentMethod::getId).collect(Collectors.toList()));
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException(e, HttpStatus.BAD_REQUEST);
		}
	}
}
