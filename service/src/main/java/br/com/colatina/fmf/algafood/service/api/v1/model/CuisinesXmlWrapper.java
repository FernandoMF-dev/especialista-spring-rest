package br.com.colatina.fmf.algafood.service.api.v1.model;

import br.com.colatina.fmf.algafood.service.domain.service.dto.CuisineDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JacksonXmlRootElement(localName = "cuisines")
@RequiredArgsConstructor
public class CuisinesXmlWrapper implements Serializable {

	@JsonProperty("cuisine")
	@JacksonXmlElementWrapper(useWrapping = false)
	private final List<CuisineDto> cuisines;

}
