package br.com.colatina.fmf.algafood.service.repository;

import br.com.colatina.fmf.algafood.service.domain.entity.Kitchen;

import java.util.List;

public interface KitchenRepository {

	List<Kitchen> findAll();

	Kitchen findById(Long id);

	Kitchen save(Kitchen entity);

	void delete(Kitchen entity);

}
