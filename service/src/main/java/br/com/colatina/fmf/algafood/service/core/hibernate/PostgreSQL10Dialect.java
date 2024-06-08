package br.com.colatina.fmf.algafood.service.core.hibernate;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class PostgreSQL10Dialect extends org.hibernate.dialect.PostgreSQL10Dialect {
	public PostgreSQL10Dialect() {
		super();
		registerFunction("at_time_zone", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "(?1 AT TIME ZONE ?2)"));
	}
}
