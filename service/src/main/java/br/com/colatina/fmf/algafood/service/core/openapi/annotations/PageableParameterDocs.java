package br.com.colatina.fmf.algafood.service.core.openapi.annotations;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Parameter(in = ParameterIn.QUERY, name = "page", description = "PAGE: Page number (0..N)", schema = @Schema(type = "integer", defaultValue = "0"))
@Parameter(in = ParameterIn.QUERY, name = "size", description = "PAGE: Number of elements per page", schema = @Schema(type = "integer", defaultValue = "10"))
@Parameter(in = ParameterIn.QUERY, name = "sort", description = "PAGE: Sorting criteria: property(asc|desc)", schema = @Schema(type = "string", defaultValue = "name,DESC"))
public @interface PageableParameterDocs {
}
