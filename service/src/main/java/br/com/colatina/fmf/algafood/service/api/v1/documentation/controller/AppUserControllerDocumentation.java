package br.com.colatina.fmf.algafood.service.api.v1.documentation.controller;

import br.com.colatina.fmf.algafood.service.core.openapi.SpringDocUtils;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PasswordChangeDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = SpringDocUtils.SECURITY_SCHEME_NAME)
public interface AppUserControllerDocumentation {
    CollectionModel<AppUserDto> findAll();

    ResponseEntity<AppUserDto> findById(Long id);

    ResponseEntity<AppUserDto> insert(AppUserInsertDto dto);

    ResponseEntity<AppUserDto> update(Long id, AppUserDto dto);

    ResponseEntity<Void> changePassword(Long id, PasswordChangeDto dto);

    ResponseEntity<Void> delete(Long id);
}
