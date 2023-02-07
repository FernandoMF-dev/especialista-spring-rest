package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFound;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantCrudService {
	private final RestaurantRepository restaurantRepository;
	private final RestaurantMapper restaurantMapper;

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

		Restaurant entity = restaurantMapper.toEntity(dto);
		entity = restaurantRepository.save(entity);
		return restaurantMapper.toDto(entity);
	}
}
