function consultar() {
	$.ajax({
		url: "http://localhost:8080/api/payment-methods",
		type: "GET",

		success: function(response) {
			preencherTabela(response);
		}
	});
}

function preencherTabela(formasPagamento) {
	$("#tabela tbody tr").remove();

	$.each(formasPagamento, function(i, formaPagamento) {
		const linha = $("<tr>");

		linha.append(
			$("<td>").text(formaPagamento.id),
			$("<td>").text(formaPagamento.description)
		);

		linha.appendTo("#tabela");
	});
}


$("#btn-consultar").click(consultar);
