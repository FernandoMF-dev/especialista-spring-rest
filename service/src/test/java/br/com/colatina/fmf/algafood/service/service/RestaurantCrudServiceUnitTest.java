package br.com.colatina.fmf.algafood.service.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.repository.RestaurantRepository;
import br.com.colatina.fmf.algafood.service.domain.service.RestaurantCrudService;
import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantCrudServiceUnitTest extends BaseCommonIntTest {
	@Autowired
	RestaurantCrudService restaurantCrudService;

	@MockBean
	RestaurantRepository restaurantRepository;

	@Test
	void findFirst_fail_noneFound() {
		Mockito.doReturn(Optional.empty()).when(restaurantRepository).findFirst();

		Assertions.assertThrows(ResourceNotFoundException.class, () -> restaurantCrudService.findFirst());
	}
}
