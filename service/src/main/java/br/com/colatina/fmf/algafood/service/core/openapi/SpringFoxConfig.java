package br.com.colatina.fmf.algafood.service.core.openapi;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.function.Consumer;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	@Bean
	public Docket api() {
		var typeResolver = new TypeResolver();

		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.colatina.fmf.algafood.service.api.controller"))
				.paths(PathSelectors.any())
				.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPutResponseMessages())
				.globalResponses(HttpMethod.PATCH, globalPatchResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(ApiErrorResponse.class))
				.apiInfo(apiInfo())
				.tags(new Tag(SpringFoxControllerTags.CUISINES, "Operations related to cuisines"));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("Open API for customers and restaurants")
				.version("1.0.0")
				.build();
	}

	// <editor-fold defaultstate="collapsed" desc="Global response messages">
	private List<Response> globalGetResponseMessages() {
		return List.of(
				newErrorResponseMessage(HttpStatus.NOT_FOUND, true),
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
