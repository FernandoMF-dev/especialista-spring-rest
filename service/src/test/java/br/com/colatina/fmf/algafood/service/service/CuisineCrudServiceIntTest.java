package br.com.colatina.fmf.algafood.service.service;

import br.com.colatina.fmf.algafood.service.api.v1.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceInUseException;
import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Cuisine;
import br.com.colatina.fmf.algafood.service.domain.model.Restaurant;
import br.com.colatina.fmf.algafood.service.domain.repository.CuisineRepository;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.CuisineMapper;
import br.com.colatina.fmf.algafood.service.factory.CuisineFactory;
import br.com.colatina.fmf.algafood.service.factory.RestaurantFactory;
import br.com.colatina.fmf.algafood.service.utils.BaseCommonIntTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CuisineCrudServiceIntTest extends BaseCommonIntTest {
	@Autowired
	private CuisineCrudService cuisineCrudService;
	@Autowired
	private CuisineFactory cuisineFactory;
	@Autowired
	private CuisineMapper cuisineMapper;
	@Autowired
	private CuisineRepository cuisineRepository;

	@Autowired
	private RestaurantFactory restaurantFactory;

	@Test
	public void findAll_success() {
		cuisineFactory.createAndPersist();

		List<CuisineDto> cuisines = cuisineCrudService.findAll();

		Assert.assertNotNull(cuisines);
		Assert.assertFalse(cuisines.isEmpty());
	}

	@Test
	public void findAllXml_success() {
		cuisineFactory.createAndPersist();

		CuisinesXmlWrapper cuisines = cuisineCrudService.findAllXml();

		Assert.assertNotNull(cuisines);
		Assert.assertNotNull(cuisines.getCuisines());
		Assert.assertFalse(cuisines.getCuisines().isEmpty());
	}

	@Test
	public void findDtoById_success() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		CuisineDto cuisineDto = cuisineCrudService.findDtoById(cuisine.getId());

		Assert.assertNotNull(cuisineDto);
		Assert.assertEquals(cuisineDto.getId(), cuisine.getId());
		Assert.assertEquals(cuisineDto.getName(), cuisine.getName());
	}

	@Test
	public void findDtoById_fail_nonExistentEntity() {
		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.findDtoById(NON_EXISTING_ID));
	}

	@Test
	public void findDtoById_fail_deletedEntity() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		Long cuisineId = cuisine.getId();

		cuisine.setExcluded(Boolean.TRUE);
		cuisineRepository.save(cuisine);

		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.findDtoById(cuisineId));
	}

	@Test
	public void findEntityById_success() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		Cuisine entity = cuisineCrudService.findEntityById(cuisine.getId());

		Assert.assertNotNull(entity);
		Assert.assertEquals(entity, cuisine);
	}

	@Test
	public void findEntityById_fail_nonExistentEntity() {
		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.findEntityById(NON_EXISTING_ID));
	}

	@Test
	public void findEntityById_fail_deletedEntity() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		Long cuisineId = cuisine.getId();

		cuisine.setExcluded(Boolean.TRUE);
		cuisineRepository.save(cuisine);

		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.findEntityById(cuisineId));
	}

	@Test
	public void findFirst_success() {
		cuisineFactory.createAndPersist();

		CuisineDto cuisineDto = cuisineCrudService.findFirst();

		Assert.assertNotNull(cuisineDto);
		Assert.assertNotNull(cuisineDto.getId());
	}

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

	@Test
	public void update_fail_nonExistentEntity() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		CuisineDto cuisineDto = cuisineMapper.toDto(cuisine);

		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.update(cuisineDto, NON_EXISTING_ID));
	}

	public void update_fail_deletedEntity() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		CuisineDto cuisineDto = cuisineMapper.toDto(cuisine);
		Long cuisineId = cuisine.getId();

		cuisineDto.setName(cuisineDto.getName() + " update");
		cuisine.setExcluded(Boolean.TRUE);
		cuisineRepository.save(cuisine);

		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.update(cuisineDto, cuisineId));
	}

	@Test
	public void delete_success() {
		Cuisine cuisine = cuisineFactory.createAndPersist();

		cuisineCrudService.delete(cuisine.getId());

		Cuisine deleted = cuisineFactory.getById(cuisine.getId());
		Assert.assertTrue(deleted.getExcluded());
	}

	@Test
	public void delete_fail_nonExistentEntity() {
		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.delete(NON_EXISTING_ID));
	}

	@Test
	public void delete_fail_deletedEntity() {
		Cuisine cuisine = cuisineFactory.createAndPersist();
		Long cuisineId = cuisine.getId();

		cuisine.setExcluded(Boolean.TRUE);
		cuisineRepository.save(cuisine);

		Assert.assertThrows(ResourceNotFoundException.class, () -> cuisineCrudService.delete(cuisineId));
	}

	@Test
	public void delete_fail_inUse() {
		Restaurant restaurant = restaurantFactory.createAndPersist();
		Cuisine cuisine = restaurant.getCuisine();
		Long cuisineId = cuisine.getId();

		Assert.assertThrows(ResourceInUseException.class, () -> cuisineCrudService.delete(cuisineId));
	}
}
