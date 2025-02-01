package br.com.colatina.fmf.algafood.service.core.hibernate;

import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.query.spi.QueryEngine;
import org.hibernate.query.sqm.function.SqmFunctionRegistry;
import org.hibernate.query.sqm.produce.function.FunctionParameterType;
import org.hibernate.type.BasicType;
import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.spi.TypeConfiguration;

import java.util.Date;

public class CustomPostgreSqlDialect extends PostgreSQLDialect {
	private SqmFunctionRegistry functionRegistry;
	private TypeConfiguration typeConfiguration;
	private BasicTypeRegistry basicTypeRegistry;

	public CustomPostgreSqlDialect() {
		super();
	}

	@Override
	public void initializeFunctionRegistry(QueryEngine queryEngine) {
		super.initializeFunctionRegistry(queryEngine);
		registerUtilProperties(queryEngine);

		dateTrunc();
	}

	private void registerUtilProperties(QueryEngine queryEngine) {
		functionRegistry = queryEngine.getSqmFunctionRegistry();
		typeConfiguration = queryEngine.getTypeConfiguration();
		basicTypeRegistry = typeConfiguration.getBasicTypeRegistry();
	}

	private void dateTrunc() {
		BasicType<Date> timestampType = basicTypeRegistry.resolve(StandardBasicTypes.TIMESTAMP);

		functionRegistry.patternDescriptorBuilder("date_trunc", "date_trunc(?1,?2)")
				.setInvariantType(timestampType)
				.setExactArgumentCount(2)
				.setParameterTypes(FunctionParameterType.STRING, FunctionParameterType.TEMPORAL)
				.setArgumentListSignature("(STRING field, TEMPORAL datetime)")
				.register();
	}
}
