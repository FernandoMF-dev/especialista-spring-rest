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
@Parameter(in = ParameterIn.QUERY, name = "restaurantId", description = "FILTER: ", example = "1", schema = @Schema(type = "long"))
@Parameter(in = ParameterIn.QUERY, name = "startDate", description = "FILTER: ID of the restaurant", example = "2023-01-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time"))
@Parameter(in = ParameterIn.QUERY, name = "endDate", description = "FILTER: Start date of the period", example = "2023-12-31T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
@Parameter(in = ParameterIn.QUERY, name = "timeOffset", description = "FILTER: End date of the period", example = "+03:00", schema = @Schema(type = "string", format = "offset"))
public @interface SalesPerPeriodFilterParameterDocs {
}
