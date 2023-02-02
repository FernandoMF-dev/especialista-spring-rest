package br.com.colatina.fmf.algafood.service.jpa;

import br.com.colatina.fmf.algafood.service.FmfAlgafoodServiceApplication;
import br.com.colatina.fmf.algafood.service.domain.entity.Kitchen;
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

		KitchenRegister kitchenRegister = applicationContext.getBean(KitchenRegister.class);

		Kitchen kitchen1 = new Kitchen();
		kitchen1.setName("Brasileira");

		Kitchen kitchen2 = new Kitchen();
		kitchen2.setName("Japonesa");

		kitchenRegister.save(kitchen1);
		kitchenRegister.save(kitchen2);
	}
}
