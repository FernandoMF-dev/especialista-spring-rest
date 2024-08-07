package br.com.colatina.fmf.algafood.service.domain.repository;

import br.com.colatina.fmf.algafood.service.domain.model.Product;
import br.com.colatina.fmf.algafood.service.domain.model.ProductPicture;
import br.com.colatina.fmf.algafood.service.domain.repository.queries.ProductRepositoryQueries;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CustomJpaRepository<Product, Long>, ProductRepositoryQueries {
	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto" +
			"(p.id, p.name, p.description, p.price, p.active, p.restaurant.id, p.restaurant.name) " +
			" FROM Product p " +
			" WHERE p.restaurant.id = :restaurantId " +
			" AND p.excluded = FALSE ")
	List<ProductDto> findAllDtoByRestaurant(@Param("restaurantId") Long restaurantId);

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto" +
			"(p.id, p.name, p.description, p.price, p.active, p.restaurant.id, p.restaurant.name) " +
			" FROM Product p " +
			" WHERE p.id = :productId " +
			" AND p.restaurant.id = :restaurantId " +
			" AND p.excluded = FALSE ")
	Optional<ProductDto> findDtoByIdAndRestaurant(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

	@Query("SELECT p " +
			" FROM Product p " +
			" WHERE p.id = :productId " +
			" AND p.restaurant.id = :restaurantId " +
			" AND p.excluded = FALSE ")
	Optional<Product> findEntityByIdAndRestaurant(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

	@Query("SELECT pp " +
			" FROM ProductPicture pp " +
			" INNER JOIN pp.product p " +
			" INNER JOIN p.restaurant r " +
			" WHERE p.id = :productId " +
			" AND r.id = :restaurantId " +
			" AND p.excluded = FALSE ")
	Optional<ProductPicture> findPictureEntityById(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);

	@Query("SELECT new br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto" +
			"(pp.id, pp.fileName, pp.description, pp.contentType, pp.size) " +
			" FROM ProductPicture pp " +
			" INNER JOIN pp.product p " +
			" INNER JOIN p.restaurant r " +
			" WHERE p.id = :productId " +
			" AND r.id = :restaurantId " +
			" AND p.excluded = FALSE ")
	Optional<ProductPictureDto> findPictureDtoById(@Param("restaurantId") Long restaurantId, @Param("productId") Long productId);
}
