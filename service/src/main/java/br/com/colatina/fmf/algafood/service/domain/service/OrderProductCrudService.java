package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.repository.OrderProductRepository;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderProductInsertMapper;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.OrderProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderProductCrudService {
	private final OrderProductRepository orderProductRepository;
	private final OrderProductMapper orderProductMapper;
	private final OrderProductInsertMapper orderProductInsertMapper;
}
