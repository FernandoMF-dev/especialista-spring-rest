package br.com.colatina.fmf.algafood.service.jpa;

import br.com.colatina.fmf.algafood.service.FmfAlgafoodServiceApplication;
import br.com.colatina.fmf.algafood.service.domain.model.Kitchen;
import br.com.colatina.fmf.algafood.service.domain.repository.KitchenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@Slf4j
public class KitchenRegisterMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FmfAlgafoodServiceApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		KitchenRepository kitchenRepository = applicationContext.getBean(KitchenRepository.class);

		Kitchen kitchen = new Kitchen();
		kitchen.setName("Tailandesa");
		kitchen = kitchenRepository.save(kitchen);
		log.debug(kitchen.getId() + " - " + kitchen.getName());
		kitchenRepository.delete(kitchen);
	}
}
