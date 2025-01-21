package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import br.com.colatina.fmf.algafood.service.domain.service.filter.OrderPageFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

public interface OrderControllerDocumentation {
    CollectionModel<OrderListDto> findAll();

    ResponseEntity<OrderDto> findByUuid(String uuid);

    PagedModel<OrderListDto> page(OrderPageFilter filter, Pageable pageable);

    ResponseEntity<OrderDto> insert(OrderInsertDto dto);
}
