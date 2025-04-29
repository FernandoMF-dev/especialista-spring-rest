package br.com.colatina.fmf.algafood.service.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.repository.CuisineRepository;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuisineCrudServiceUnitTest extends BaseCommonIntTest {
	@Autowired
	private CuisineCrudService cuisineCrudService;

	@MockBean
	private CuisineRepository cuisineRepository;

	@Test
	void findFirst_fail_noneFound() {
		Mockito.doReturn(Optional.empty()).when(cuisineRepository).findFirst();

		Assertions.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.findFirst());
	}
}
