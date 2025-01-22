package br.com.colatina.fmf.algafood.service.core.openapi;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
@SecurityScheme(name = SpringDocUtils.SECURITY_SCHEME_NAME, type = SecuritySchemeType.OAUTH2,
		flows = @OAuthFlows(authorizationCode = @OAuthFlow(
				authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
				tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
				scopes = {
						@OAuthScope(name = "READ", description = "read scope"),
						@OAuthScope(name = "WRITE", description = "write scope"),
						@OAuthScope(name = "DELETE", description = "delete scope"),
				}
		)))
public class SpringDocConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(apiInfo())
				.externalDocs(externalDocs())
				.tags(tags());
	}

	@Bean
	public OpenApiCustomiser openApiCustomiser() {
		return openApi -> openApi.getPaths()
				.values()
				.forEach(pathItem -> pathItem.readOperationsMap()
						.forEach((httpMethod, operation) -> mapGlobalApiResponsesPerHttpMethod(httpMethod, operation, openApi.getComponents())));
	}

	private Info apiInfo() {
		return new Info()
				.title("Algafood API")
				.description("Open API for customers and restaurants")
				.version("1.0.0")
				.license(license());
	}

	private License license() {
		return new License()
				.name("Apache 2.0")
				.url("https://springdoc.com");
	}

	private ExternalDocumentation externalDocs() {
		return new ExternalDocumentation()
				.description("Algafood Docs")
				.url("https://algafood-fmf.com/docs");
	}

	private List<Tag> tags() {
		return List.of(
				new Tag().name(SpringDocControllerTags.CITIES).description("Operations related to the register of addresses cities"),
				new Tag().name(SpringDocControllerTags.ROOT).description("Operations related to the root entry point of the API")
		);
	}

	private void mapGlobalApiResponsesPerHttpMethod(PathItem.HttpMethod httpMethod, Operation operation, Components components) {
		ApiResponses responses = operation.getResponses();

		switch (httpMethod) {
			case GET:
				addApiResponse(HttpStatus.NOT_FOUND, responses, components);
				addApiResponse(HttpStatus.NOT_ACCEPTABLE, responses, null);
				addApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, responses, components);
				break;
			case POST:
				addApiResponse(HttpStatus.BAD_REQUEST, responses, components);
				addApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, responses, components);
				break;
			case PUT, PATCH:
				addApiResponse(HttpStatus.BAD_REQUEST, responses, components);
				addApiResponse(HttpStatus.NOT_FOUND, responses, components);
				addApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, responses, components);
				break;
			case DELETE:
				addApiResponse(HttpStatus.NOT_FOUND, responses, components);
				addApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, responses, components);
				break;
			default:
				addApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, responses, components);
				break;
		}
	}

	private void addApiResponse(HttpStatus httpStatus, ApiResponses responses, Components components) {
		String status = String.valueOf(httpStatus.value());
		ApiResponse internalServerError = new ApiResponse().description(httpStatus.getReasonPhrase());

		if (Objects.nonNull(components)) {
			MediaType mediaType = new MediaType().schema(AnnotationsUtils.resolveSchemaFromType(ApiErrorResponse.class, components, null));
			Content content = new Content().addMediaType(APPLICATION_JSON_VALUE, mediaType);
			internalServerError.content(content);
		}

		responses.addApiResponse(status, internalServerError);
	}
}
