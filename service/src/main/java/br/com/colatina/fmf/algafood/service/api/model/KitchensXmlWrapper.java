package br.com.colatina.fmf.algafood.service.api.model;

import br.com.colatina.fmf.algafood.service.domain.service.dto.KitchenDto;
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
@JacksonXmlRootElement(localName = "kitchens")
@RequiredArgsConstructor
public class KitchensXmlWrapper implements Serializable {

	@JsonProperty("kitchen")
	@JacksonXmlElementWrapper(useWrapping = false)
	private final List<KitchenDto> kitchens;

}
