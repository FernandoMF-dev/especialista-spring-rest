package br.com.colatina.fmf.algafood.service.api.hateoas;

import br.com.colatina.fmf.algafood.service.api.controller.StatisticsController;
import br.com.colatina.fmf.algafood.service.api.model.HypermediaModel;
import br.com.colatina.fmf.algafood.service.domain.service.statistics.SalesPerPeriod;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class StatisticsHateoas {
	public HypermediaModel getRootHypermediaModel() {
		HypermediaModel root = new HypermediaModel();

		root.add(getSalesPerPeriodLink("sales-per-day"));
		root.add(getSalesPerPeriodLink("sales-per-month"));
		root.add(getSalesPerPeriodLink("sales-per-year"));

		return root;
	}

	public CollectionModel<SalesPerPeriod> mapSalesPerPeriodModel(Iterable<SalesPerPeriod> salesPerDay, String path) {
		CollectionModel<SalesPerPeriod> collectionModel = CollectionModel.of(salesPerDay);
		collectionModel.add(getSalesPerPeriodLink(path, IanaLinkRelations.SELF));
		return collectionModel;
	}

	private Link getSalesPerPeriodLink(String path) {
		return getSalesPerPeriodLink(path, LinkRelation.of(path));
	}

	private Link getSalesPerPeriodLink(String path, LinkRelation rel) {
		URI uri = linkTo(StatisticsController.class).slash(path).toUri();
		TemplateVariables filterVariables = getSalesPerPeriodFilterQueryParams();
		UriTemplate url = UriTemplate.of(uri.toString(), filterVariables);
		return Link.of(url, rel);
	}

	private TemplateVariables getSalesPerPeriodFilterQueryParams() {
		return new TemplateVariables(
				new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
				new TemplateVariable("startDate", VariableType.REQUEST_PARAM),
				new TemplateVariable("endDate", VariableType.REQUEST_PARAM),
				new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM)
		);
	}
}
