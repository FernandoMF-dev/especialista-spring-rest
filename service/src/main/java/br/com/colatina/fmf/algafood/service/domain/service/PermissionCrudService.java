package br.com.colatina.fmf.algafood.service.domain.service;

import br.com.colatina.fmf.algafood.service.domain.exceptions.ResourceNotFoundException;
import br.com.colatina.fmf.algafood.service.domain.model.Permission;
import br.com.colatina.fmf.algafood.service.domain.repository.PermissionRepository;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import br.com.colatina.fmf.algafood.service.domain.service.mapper.PermissionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionCrudService {
	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	public Permission findEntityById(Long id) {
		return permissionRepository.findByIdAndExcludedIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("permission.not_found", id));
	}

	public Set<PermissionDto> findAllDtoByProfile(Long profileId) {
		return permissionRepository.findAllDtoByProfile(profileId);
	}
}
