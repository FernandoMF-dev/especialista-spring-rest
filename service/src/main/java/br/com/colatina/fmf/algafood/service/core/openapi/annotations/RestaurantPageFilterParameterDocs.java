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
@Parameter(in = ParameterIn.QUERY, name = "name", description = "FILTER: Name of the restaurant", schema = @Schema(type = "string", defaultValue = "Burger King"))
@Parameter(in = ParameterIn.QUERY, name = "minFreightFee", description = "FILTER: Minimum freight fee", schema = @Schema(type = "double", defaultValue = "5.00"))
@Parameter(in = ParameterIn.QUERY, name = "maxFreightFee", description = "FILTER: Maximum freight fee", schema = @Schema(type = "double", defaultValue = "15.00"))
@Parameter(in = ParameterIn.QUERY, name = "active", description = "FILTER: Active status of the restaurant", schema = @Schema(type = "boolean", defaultValue = "true"))
@Parameter(in = ParameterIn.QUERY, name = "cuisineId", description = "FILTER: ID of the cuisine", schema = @Schema(type = "long", defaultValue = "1"))
public @interface RestaurantPageFilterParameterDocs {
}
