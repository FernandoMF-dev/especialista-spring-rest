package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.repository.ProductRepository;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductCrudService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
}
