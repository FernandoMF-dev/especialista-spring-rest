package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantFormDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.RestaurantPageFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantControllerDocumentation {

	CollectionModel<RestaurantListDto> findAll();

	CollectionModel<RestaurantListDto> filterByFreightFee(String name, Double min, Double max);

	PagedModel<RestaurantListDto> page(RestaurantPageFilter filter, Pageable pageable);

	ResponseEntity<RestaurantDto> findFirst();

	ResponseEntity<RestaurantDto> findById(Long id);

	ResponseEntity<RestaurantDto> insert(RestaurantFormDto dto);

	ResponseEntity<RestaurantDto> update(RestaurantFormDto dto, Long id);

	ResponseEntity<Void> toggleActive(Long id, Boolean active);

	ResponseEntity<Void> toggleAllActive(Boolean active, List<Long> restaurantIds);

	ResponseEntity<Void> toggleOpen(Long id, Boolean open);

	ResponseEntity<Void> delete(Long id);
}
