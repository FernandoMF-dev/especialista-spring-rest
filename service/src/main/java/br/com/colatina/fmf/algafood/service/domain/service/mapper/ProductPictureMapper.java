package br.com.colatina.fmf.algafood.service.domain.service.mapper;

import br.com.colatina.fmf.algafood.service.domain.model.ProductPicture;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductPictureInsertDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ProductPictureMapper extends EntityMapper<ProductPictureDto, ProductPicture> {
	@Mapping(source = "file.contentType", target = "contentType")
	@Mapping(source = "file.size", target = "size")
	@Mapping(source = "file.originalFilename", target = "fileName")
	ProductPicture toEntity(ProductPictureInsertDto dto);
}
