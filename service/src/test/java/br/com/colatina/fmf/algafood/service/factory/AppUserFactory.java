package br.com.colatina.fmf.algafood.service.factory;

import br.com.colatina.fmf.algafood.service.domain.model.AppUser;
import br.com.colatina.fmf.algafood.service.domain.repository.AppUserRepository;
import br.com.colatina.fmf.algafood.service.domain.service.AppUserCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.AppUserInsertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppUserFactory extends BaseEntityFactory<AppUser> {
	public static final String MOCK_VALID_PASSWORD = "lD71J>f6%5T)";
	public static final String MOCK_INVALID_PASSWORD = "abc123";

	@Autowired
	AppUserCrudService appUserCrudService;
	@Autowired
	AppUserRepository appUserRepository;

	@Override
	public AppUser createEntity() {
		AppUser appUser = new AppUser();
		appUser.setName(String.format("User %d", System.currentTimeMillis()));
		appUser.setEmail(String.format("user.%d@email.com", System.currentTimeMillis()));
		appUser.setPassword(MOCK_VALID_PASSWORD);
		return appUser;
	}

	@Override
	protected AppUser persist(AppUser entity) {
		AppUserInsertDto insert = mapInsertDto(entity);
		AppUserDto dto = appUserCrudService.insert(insert);
		return getById(dto.getId());
	}

	@Override
	public AppUser getById(Long id) {
		return appUserRepository.findById(id).orElse(null);
	}

	public AppUserInsertDto mapInsertDto(AppUser entity) {
		AppUserInsertDto dto = new AppUserInsertDto();

		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setEmail(entity.getEmail());
		dto.setPassword(entity.getPassword());

		return dto;
	}
}
