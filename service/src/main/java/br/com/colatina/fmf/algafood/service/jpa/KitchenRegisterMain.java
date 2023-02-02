package br.com.colatina.fmf.algafood.service.jpa;

import br.com.colatina.fmf.algafood.service.FmfAlgafoodServiceApplication;
import br.com.colatina.fmf.algafood.service.domain.entity.Kitchen;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

public class KitchenRegisterMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(FmfAlgafoodServiceApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		KitchenRegister kitchenRegister = applicationContext.getBean(KitchenRegister.class);

		List<Kitchen> kitchens = kitchenRegister.findAll();

		kitchens.forEach(value -> System.out.println(value.getNome()));
	}
}
