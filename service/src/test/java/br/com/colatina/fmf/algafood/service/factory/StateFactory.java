package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.State;
import br.com.colatina.fmf.algafood.service.domain.repository.StateRepository;
import br.com.colatina.fmf.algafood.service.domain.service.StateCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.StateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StateFactory extends BaseEntityFactory<State> {
	@Autowired
	StateMapper stateMapper;
	@Autowired
	StateCrudService stateCrudService;
	@Autowired
	StateRepository stateRepository;

	@Override
	public State createEntity() {
		State state = new State();
		state.setName(String.format("State %d", System.currentTimeMillis()));
		state.setAcronym(String.valueOf(System.currentTimeMillis()).substring(0, 2));
		return state;
	}

	@Override
	protected State persist(State entity) {
		StateDto dto = stateMapper.toDto(entity);
		dto = stateCrudService.insert(dto);
		return stateMapper.toEntity(dto);
	}

	@Override
	public State getById(Long id) {
		return stateRepository.findById(id).orElse(null);
	}
}
