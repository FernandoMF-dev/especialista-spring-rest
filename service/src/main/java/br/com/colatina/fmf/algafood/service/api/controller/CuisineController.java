package br.com.colatina.fmf.algafood.service.api.controller;

import br.com.colatina.fmf.algafood.service.api.handler.ApiErrorResponse;
import br.com.colatina.fmf.algafood.service.api.model.CuisinesXmlWrapper;
import br.com.colatina.fmf.algafood.service.core.openapi.SpringFoxControllerTags;
import br.com.colatina.fmf.algafood.service.domain.service.CuisineCrudService;
import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SpringFoxControllerTags.CUISINES)
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cuisines")
public class CuisineController {
	private final CuisineCrudService cuisineCrudService;

	@ApiOperation("Find a list of all available cuisines")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CuisineDto>> findAll() {
		log.debug("REST request to find all cuisines");
		return new ResponseEntity<>(cuisineCrudService.findAll(), HttpStatus.OK);
	}

	@ApiOperation("Find a list of all available cuisines")
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public ResponseEntity<CuisinesXmlWrapper> findAllXml() {
		log.debug("REST request to find all cuisines with the response on the XML format");
		return new ResponseEntity<>(cuisineCrudService.findAllXml(), HttpStatus.OK);
	}

	@ApiOperation("Find a cuisine by its ID")
	@ApiResponse(responseCode = "400", description = "Invalid Cuisine ID", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@ApiResponse(responseCode = "404", description = "Cuisine not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@GetMapping("/{id}")
	public ResponseEntity<CuisineDto> findById(@ApiParam(value = "ID of a available cuisine", example = "1") @PathVariable Long id) {
		log.debug("REST request to find the cuisine with ID: {}", id);
		return new ResponseEntity<>(cuisineCrudService.findDtoById(id), HttpStatus.OK);
	}

	@ApiOperation("Find the first available cuisine it can")
	@ApiResponse(responseCode = "404", description = "No cuisine found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@GetMapping("/first")
	public ResponseEntity<CuisineDto> findFirst() {
		log.debug("REST request to find the first cuisine it can");
		return new ResponseEntity<>(cuisineCrudService.findFirst(), HttpStatus.OK);
	}

	@ApiOperation("Insert a new cuisine")
	@ApiResponse(responseCode = "201", description = "Cuisine created", content = @Content(schema = @Schema(implementation = CuisineDto.class)))
	@PostMapping()
	public ResponseEntity<CuisineDto> insert(@ApiParam(name = "body", value = "Cuisine data to create")
											 @Valid @RequestBody CuisineDto dto) {
		log.debug("REST request to insert a new cuisine: {}", dto);
		return new ResponseEntity<>(cuisineCrudService.insert(dto), HttpStatus.CREATED);
	}

	@ApiOperation("Update a cuisine by its ID")
	@ApiResponse(responseCode = "200", description = "Cuisine updated", content = @Content(schema = @Schema(implementation = CuisineDto.class)))
	@ApiResponse(responseCode = "404", description = "Cuisine not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@PutMapping("/{id}")
	public ResponseEntity<CuisineDto> update(@ApiParam(value = "ID of a available cuisine to update", example = "1") @PathVariable Long id,
											 @ApiParam(name = "body", value = "Cuisine data to update")
											 @Valid @RequestBody CuisineDto dto) {
		log.debug("REST request to update cuisine with id {}: {}", id, dto);
		return new ResponseEntity<>(cuisineCrudService.update(dto, id), HttpStatus.OK);
	}

	@ApiOperation("Delete a cuisine by its ID")
	@ApiResponse(responseCode = "204", description = "Cuisine deleted")
	@ApiResponse(responseCode = "404", description = "Cuisine not found", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@ApiParam(value = "ID of a available cuisine to delete", example = "1") @PathVariable Long id) {
		log.debug("REST request to delete cuisine with id {}", id);
		cuisineCrudService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
