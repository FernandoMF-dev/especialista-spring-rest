package br.com.colatina.fmf.algafood.service.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	@Bean
	public Docket api() {
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

	private List<Response> globalGetResponseMessages() {
		return List.of(
				newResponseMessage(HttpStatus.NOT_FOUND),
				newResponseMessage(HttpStatus.NOT_ACCEPTABLE),
				newResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}

	private List<Response> globalPostResponseMessages() {
		return List.of(
				newResponseMessage(HttpStatus.BAD_REQUEST),
				newResponseMessage(HttpStatus.NOT_ACCEPTABLE),
				newResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE),
				newResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}

	private List<Response> globalPutResponseMessages() {
		return List.of(
				newResponseMessage(HttpStatus.BAD_REQUEST),
				newResponseMessage(HttpStatus.NOT_FOUND),
				newResponseMessage(HttpStatus.NOT_ACCEPTABLE),
				newResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE),
				newResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}

	private List<Response> globalPatchResponseMessages() {
		return List.of(
				newResponseMessage(HttpStatus.NOT_FOUND),
				newResponseMessage(HttpStatus.BAD_REQUEST),
				newResponseMessage(HttpStatus.NOT_ACCEPTABLE),
				newResponseMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE),
				newResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}

	private List<Response> globalDeleteResponseMessages() {
		return List.of(
				newResponseMessage(HttpStatus.BAD_REQUEST),
				newResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR)
		);
	}

	private Response newResponseMessage(HttpStatus httpStatus) {
		return new ResponseBuilder()
				.code(String.valueOf(httpStatus.value()))
				.description(httpStatus.getReasonPhrase())
				.build();
	}
}
