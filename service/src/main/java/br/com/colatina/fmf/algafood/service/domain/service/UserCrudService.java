package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.repository.UserRepository;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCrudService {
	private final UserRepository userRepository;
	private final UserMapper userMapper;
}
