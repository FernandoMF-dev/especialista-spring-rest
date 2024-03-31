package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.model.Order;
import br.com.colatina.fmf.algafood.service.domain.model.OrderProduct;
import br.com.colatina.fmf.algafood.service.domain.repository.OrderProductRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderProductInsertMapper;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProductCrudService {
	private final OrderProductRepository orderProductRepository;
	private final OrderProductMapper orderProductMapper;
	private final OrderProductInsertMapper orderProductInsertMapper;

	public List<OrderProductDto> insertItensOnOrder(List<OrderProduct> products, Long orderId) {
		products.forEach(product -> product.setOrder(new Order(orderId)));
		products = orderProductRepository.saveAll(products);
		return orderProductMapper.toDto(products);
	}
}
