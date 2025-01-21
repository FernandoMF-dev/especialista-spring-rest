package br.com.colatina.fmf.algafood.service.api.v2.documentation.controller;

import br.com.colatina.fmf.algafood.service.api.v2.model.CityModelV2;
import br.com.colatina.fmf.algafood.service.api.v2.model.input.CityInputV2;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface CityControllerV2Documentation {
    CollectionModel<CityModelV2> findAll();

    ResponseEntity<CityModelV2> findById(Long id);

    ResponseEntity<CityModelV2> insert(CityInputV2 input);

    ResponseEntity<CityModelV2> update(Long id, CityInputV2 input);

    ResponseEntity<Void> delete(Long id);
}
