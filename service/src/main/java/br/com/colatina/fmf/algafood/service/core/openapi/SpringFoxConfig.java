package br.com.colatina.fmf.algafood.service.core.openapi;

import br.com.colatina.fmf.algafood.service.api.documentation.model.LinksModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.PageableModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.CityCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.CuisineCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.OrderCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.PaymentMethodCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.PermissionCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.ProductCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.ProfileCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.RestaurantCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.SalesPerPeriodCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.StateCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.collection.UserCollectionModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.page.OrderPageModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.page.RestaurantPageModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CityDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PaymentMethodDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.PermissionDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProductDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.ProfileDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.RestaurantListDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.StateDto;
import br.com.colatina.fmf.algafood.service.domain.service.dto.UserDto;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.Example;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.function.Consumer;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	@Value("${algafood.squiggly.param-name}")
	private String squigglyParamName;

	@Bean
	public JacksonModuleRegistrar springFoxJacksonConfig() {
		return objectMapper -> objectMapper.registerModule(new JavaTimeModule());
	}

	@Bean
	public Docket api() {
		var docket = startDocketBuild();

		setGlobalResponseMessages(docket);
		setGlobalOperationParameters(docket);
		setRepresentationModelsConfig(docket);
		setApiInfo(docket);
		setControllerTags(docket);

		return docket;
	}

	private Docket startDocketBuild() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.colatina.fmf.algafood.service.api.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	private void setGlobalResponseMessages(Docket docket) {
		docket.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages())
				.globalResponses(HttpMethod.PATCH, globalPatchResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages());
	}

	private void setGlobalOperationParameters(Docket docket) {
		docket.globalRequestParameters(List.of(
				new RequestParameterBuilder()
						.name(squigglyParamName)
						.description("Names of properties to filter in the response, separated by commas")
						.example(new Example("name,description"))
						.in(ParameterType.QUERY)
						.required(false)
						.query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
						.build()
		));
	}

	private void setRepresentationModelsConfig(Docket docket) {
		var typeResolver = new TypeResolver();

		docket.additionalModels(typeResolver.resolve(ApiErrorResponse.class));

		docket.ignoredParameterTypes(ServletWebRequest.class, LinkRelation.class);

		docket.directModelSubstitute(Pageable.class, PageableModelOpenApi.class);
		docket.directModelSubstitute(Links.class, LinksModelOpenApi.class);

		// A substituição de modelos usando a função `alternateTypeRules()` não funciona caso a resposta esteja encapsulado em um `ResponseEntity` na configuração do endpoint.
		// Considerando que o SpringFox não está mais recebendo atualizações, esse bug nunca será corrigido.
		// Eventualmente a intenção do curso é substituir a documentação do SpringFox pelo SpringDoc. Espero que o SpringDoc não tenha esse problema.
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CityDto.class), CityCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, CuisineDto.class), CuisineCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, OrderListDto.class), OrderCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PaymentMethodDto.class), PaymentMethodCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, PermissionDto.class), PermissionCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, ProductDto.class), ProductCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, ProfileDto.class), ProfileCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, RestaurantListDto.class), RestaurantCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, SalesPerPeriod.class), SalesPerPeriodCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, StateDto.class), StateCollectionModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(CollectionModel.class, UserDto.class), UserCollectionModelOpenApi.class));

		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, OrderListDto.class), OrderPageModelOpenApi.class));
		docket.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(PagedModel.class, RestaurantListDto.class), RestaurantPageModelOpenApi.class));
	}

	private void setApiInfo(Docket docket) {
		var apiInfo = new ApiInfoBuilder()
				.title("Algafood API")
				.description("Open API for customers and restaurants")
				.version("1.0.0")
				.build();

		docket.apiInfo(apiInfo);
	}

	private void setControllerTags(Docket docket) {
		docket.tags(
				new Tag(SpringFoxControllerTags.CITIES, "Operations related to the register of addresses cities"),
				new Tag(SpringFoxControllerTags.CUISINES, "Operations related to the register of cuisines"),
				new Tag(SpringFoxControllerTags.ORDERS, "Operations related to the emission and management of customer orders"),
				new Tag(SpringFoxControllerTags.PAYMENT_METHODS, "Operations related to the register of payment methods"),
				new Tag(SpringFoxControllerTags.PRODUCTS, "Operations related to the register and management of the products offered by restaurants"),
				new Tag(SpringFoxControllerTags.PROFILES, "Operations related to the register of users profiles"),
				new Tag(SpringFoxControllerTags.ROOT, "Operations related to the root entry point of the API"),
				new Tag(SpringFoxControllerTags.RESTAURANTS, "Operations related to the register and management of restaurants"),
				new Tag(SpringFoxControllerTags.STATES, "Operations related to the register of addresses states"),
				new Tag(SpringFoxControllerTags.STATISTICS, "Operations related to the generation of statistics and reports"),
				new Tag(SpringFoxControllerTags.USERS, "Operations related to the register and management of users accounts")
		);
	}

	// <editor-fold defaultstate="collapsed" desc="Global response messages">
	private List<Response> globalGetResponseMessages() {
		return List.of(
				newErrorResponseMessage(HttpStatus.NOT_ACCEPTABLE, false),
				newErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, true)
		);
	}

	private List<Response> globalPostResponseMessages() {
		return List.of(
				newErrorResponseMessage(HttpStatus.BAD_REQUEST, true),
				newErrorResponseMessage(HttpStatus.NOT_ACCEPTABLE, false),
				newErrorResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, false),
				newErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, true)
		);
	}

	private List<Response> globalPutResponseMessages() {
		return List.of(
				newErrorResponseMessage(HttpStatus.BAD_REQUEST, true),
				newErrorResponseMessage(HttpStatus.NOT_FOUND, true),
				newErrorResponseMessage(HttpStatus.NOT_ACCEPTABLE, false),
				newErrorResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, false),
				newErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, true)
		);
	}

	private List<Response> globalPatchResponseMessages() {
		return List.of(
				newErrorResponseMessage(HttpStatus.NOT_FOUND, true),
				newErrorResponseMessage(HttpStatus.BAD_REQUEST, true),
				newErrorResponseMessage(HttpStatus.NOT_ACCEPTABLE, false),
				newErrorResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, false),
				newErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, true)
		);
	}

	private List<Response> globalDeleteResponseMessages() {
		return List.of(
				newErrorResponseMessage(HttpStatus.BAD_REQUEST, true),
				newErrorResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR, true)
		);
	}

	private Response newErrorResponseMessage(HttpStatus httpStatus, boolean doesReturnBody) {
		var responseBuilder = new ResponseBuilder()
				.code(String.valueOf(httpStatus.value()))
				.description(httpStatus.getReasonPhrase());

		if (doesReturnBody) {
			responseBuilder
					.representation(MediaType.APPLICATION_JSON)
					.apply(getProblemaModelReference());
		}

		return responseBuilder.build();
	}

	private Consumer<RepresentationBuilder> getProblemaModelReference() {
		return r -> r.model(m -> m.name("ApiErrorResponse")
				.referenceModel(ref -> ref.key(k -> k.qualifiedModelName(
						q -> q.name("ApiErrorResponse").namespace("br.com.colatina.fmf.algafood.service.api.handler")))));
	}
	// </editor-fold>
}
