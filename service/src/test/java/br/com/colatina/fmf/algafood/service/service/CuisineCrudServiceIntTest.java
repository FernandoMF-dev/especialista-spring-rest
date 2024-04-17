package br.com.colatina.fmf.algafood.service.service;

import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import br.com.colatina.fmf.algafood.service.factory.CuisineFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CuisineCrudServiceIntTest {
	@Autowired
	private CuisineCrudService cuisineCrudService;
	@Autowired
	private CuisineFactory cuisineFactory;
	@Autowired
	private CuisineMapper cuisineMapper;

	@Test
	public void insert_success() {
		Cuisine cuisine = cuisineFactory.createEntity();
		CuisineDto cuisineDto = cuisineMapper.toDto(cuisine);
		CuisineDto insertedDto = cuisineCrudService.insert(cuisineDto);

		Assert.assertNotNull(insertedDto);
		Assert.assertNotNull(insertedDto.getId());
		Assert.assertEquals(cuisineDto.getName(), insertedDto.getName());
	}

	@Test
	public void update_success() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		CuisineDto cuisineDto = cuisineMapper.toDto(cuisine);

		cuisineDto.setName(cuisineDto.getName() + " update");

		CuisineDto editedDto = cuisineCrudService.update(cuisineDto, cuisineDto.getId());

		Assert.assertNotNull(editedDto);
		Assert.assertNotNull(editedDto.getId());
		Assert.assertEquals(editedDto.getId(), cuisine.getId());
		Assert.assertNotEquals(cuisine.getName(), editedDto.getName());
	}
}
