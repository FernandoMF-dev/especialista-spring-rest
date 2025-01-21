package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface CityControllerDocumentation {
    CollectionModel<CityDto> findAll();

    ResponseEntity<CityDto> findById(Long id);

    ResponseEntity<CityDto> insert(CityDto dto);

    ResponseEntity<CityDto> update(Long id, CityDto dto);

    ResponseEntity<Void> delete(Long id);
}
