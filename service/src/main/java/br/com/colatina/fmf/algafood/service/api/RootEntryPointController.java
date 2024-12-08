package br.com.colatina.fmf.algafood.service.api;

import br.com.colatina.fmf.algafood.service.api.v1.controller.AppUserController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.CityController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.CuisineController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.OrderController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.PaymentMethodController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.ProfileController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.RestaurantController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.StateController;
import br.com.colatina.fmf.algafood.service.api.v1.controller.StatisticsController;
import br.com.colatina.fmf.algafood.service.api.v1.documentation.controller.RootEntryPointControllerDocumentation;
import br.com.colatina.fmf.algafood.service.api.v1.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.api.v2.controller.CityControllerV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController implements RootEntryPointControllerDocumentation {
	@GetMapping()
	@Override
	public HypermediaModel root() {
		log.debug("REST request to get root entry point");
		HypermediaModel root = new HypermediaModel();

		root.add(linkTo(methodOn(RootEntryPointController.class).rootApi()).withRel("api"));
		root.add(linkTo(RootEntryPointController.class).slash("swagger-ui/index.html#").withRel("documentation_swagger"));
		root.add(linkTo(RootEntryPointController.class).slash("v3/api-docs").withRel("documentation_openapi"));

		return root;
	}

	@GetMapping("/api")
	@Override
	public HypermediaModel rootApi() {
		log.debug("REST request to get root entry point of the API");
		HypermediaModel root = new HypermediaModel();

		root.add(linkTo(methodOn(RootEntryPointController.class).rootApiV1()).withRel("v1"));
		root.add(linkTo(methodOn(RootEntryPointController.class).rootApiV2()).withRel("v2"));

		return root;
	}

	@GetMapping("/api/v1")
	@Override
	public HypermediaModel rootApiV1() {
		log.debug("REST request to get root entry point of the API (v1)");
		HypermediaModel root = new HypermediaModel();

		root.add(linkTo(CityController.class).withRel("cities"));
		root.add(linkTo(CuisineController.class).withRel("cuisines"));
		root.add(linkTo(OrderController.class).withRel("orders"));
		root.add(linkTo(PaymentMethodController.class).withRel("payment-methods"));
		root.add(linkTo(ProfileController.class).withRel("profiles"));
		root.add(linkTo(RestaurantController.class).withRel("restaurants"));
		root.add(linkTo(StatisticsController.class).withRel("statistics"));
		root.add(linkTo(StateController.class).withRel("states"));
		root.add(linkTo(AppUserController.class).withRel("users"));

		return root;
	}

	@GetMapping("/api/v2")
	@Override
	public HypermediaModel rootApiV2() {
		log.debug("REST request to get root entry point of the API (v2)");
		HypermediaModel root = new HypermediaModel();

		root.add(linkTo(CityControllerV2.class).withRel("cities"));

		return root;
	}

	@GetMapping("/hostcheck")
	@Override
	public String checkHost() throws UnknownHostException {
		log.debug("REST request check the availability of the host");
		return InetAddress.getLocalHost().getHostAddress() + " - " + InetAddress.getLocalHost().getHostName();
	}
}
