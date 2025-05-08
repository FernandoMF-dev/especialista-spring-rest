package br.com.colatina.fmf.algafood.service.controller;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.service.ProductPictureCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import br.com.colatina.fmf.algafood.service.factory.ProductFactory;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RestaurantProductPictureControllerIntTest extends BaseCommonControllerIntTest {
	private static final String API_PRODUCT_PICTURE = "/api/v1/restaurants/{restaurantId}/products/{productId}/pictures";

	@Autowired
	private ProductFactory productFactory;
	@Autowired
	private ProductPictureCrudService productPictureCrudService;

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findPicture_success_jpg() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPictureJpg(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.contentType", Matchers.equalTo(MediaType.IMAGE_JPEG_VALUE)));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void findPicture_success_png() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPicturePng(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.contentType", Matchers.equalTo(MediaType.IMAGE_PNG_VALUE)));
	}

	@Test
	@WithMockUser(username = "tester")
	void findPicture_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPictureJpg(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void getPictureFile_success_jpg() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPictureJpg(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.IMAGE_JPEG_VALUE))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void getPictureFile_success_png() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPicturePng(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.IMAGE_PNG_VALUE))
				.andExpect(status().isOk())
				.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void getPictureFile_fail_mismatchMediaType() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPicturePng(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.IMAGE_JPEG_VALUE))
				.andExpect(status().isNotAcceptable());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"SCOPE_READ"})
	void getPictureFile_fail_missingPicture() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.IMAGE_JPEG_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void getPictureFile_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPictureJpg(restaurantId, product.getId());

		getMockMvc().perform(get(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.IMAGE_JPEG_VALUE))
				.andExpect(status().isForbidden());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void updatePicture_success_jpg() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		ProductPictureInsertDto dto = getPictureJpg();

		getMockMvc().perform(multipart(HttpMethod.PUT, API_PRODUCT_PICTURE, restaurantId, product.getId())
						.file((MockMultipartFile) dto.getFile())
						.contentType(MediaType.MULTIPART_FORM_DATA)
						.param("description", dto.getDescription()))
				.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void deletePicture_success() throws Exception {
		Product product = productFactory.createAndPersist();
		Long productId = product.getId();
		Long restaurantId = product.getRestaurant().getId();
		uploadPictureJpg(restaurantId, productId);

		getMockMvc().perform(delete(API_PRODUCT_PICTURE, restaurantId, productId)
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());

		Assertions.assertThrows(ResourceNotFoundException.class, () -> productPictureCrudService.findPictureEntity(restaurantId, productId));
	}

	@Test
	@WithMockUser(username = "tester", authorities = {"UPDATE_RESTAURANT_PRODUCT", "SCOPE_WRITE"})
	void deletePicture_fail_missingPicture() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();

		getMockMvc().perform(delete(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@WithMockUser(username = "tester")
	void deletePicture_fail_unauthorized() throws Exception {
		Product product = productFactory.createAndPersist();
		Long restaurantId = product.getRestaurant().getId();
		uploadPictureJpg(restaurantId, product.getId());

		getMockMvc().perform(delete(API_PRODUCT_PICTURE, restaurantId, product.getId())
						.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isForbidden());
	}

	private void uploadPictureJpg(Long restaurantId, Long productId) throws IOException {
		ProductPictureInsertDto dto = getPictureJpg();
		productPictureCrudService.save(dto, restaurantId, productId);
	}

	private void uploadPicturePng(Long restaurantId, Long productId) throws IOException {
		ProductPictureInsertDto dto = getPicturePng();
		productPictureCrudService.save(dto, restaurantId, productId);
	}

	private ProductPictureInsertDto getPictureJpg() {
		ProductPictureInsertDto dto = new ProductPictureInsertDto();
		dto.setDescription(String.format("Description %d", System.currentTimeMillis()));
		dto.setFile(new MockMultipartFile("file", "sample-image.jpg", MediaType.IMAGE_JPEG_VALUE, new byte[0]));
		return dto;
	}

	private ProductPictureInsertDto getPicturePng() {
		ProductPictureInsertDto dto = new ProductPictureInsertDto();
		dto.setDescription(String.format("Description %d", System.currentTimeMillis()));
		dto.setFile(new MockMultipartFile("file", "sample-image.pdf", MediaType.IMAGE_PNG_VALUE, new byte[0]));
		return dto;
	}
}
