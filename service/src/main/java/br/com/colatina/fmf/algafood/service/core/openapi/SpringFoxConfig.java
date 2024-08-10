package br.com.colatina.fmf.algafood.service.core.openapi;

import br.com.colatina.fmf.algafood.service.api.documentation.model.PageModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.documentation.model.PageableModelOpenApi;
import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.domain.service.dto.OrderListDto;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

		docket.ignoredParameterTypes(ServletWebRequest.class);

		docket.directModelSubstitute(Pageable.class, PageableModelOpenApi.class);

		// A substituição do `Page` NÃO funciona caso o `Page<?>` esteja encapsulado em um `ResponseEntity` na configuração do endpoint.
		// Considerando que o SpringFox não está mais recebendo atualizações, esse bug nunca será corrigido.
		docket.alternateTypeRules(AlternateTypeRules.newRule(
				typeResolver.resolve(Page.class, OrderListDto.class),
				typeResolver.resolve(PageModelOpenApi.class, OrderListDto.class)));
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
				new Tag(SpringFoxControllerTags.PROFILES, "Operations related to the register of users profiles"),
				new Tag(SpringFoxControllerTags.RESTAURANTS, "Operations related to the register and management of restaurants"),
				new Tag(SpringFoxControllerTags.STATES, "Operations related to the register of addresses states")
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
