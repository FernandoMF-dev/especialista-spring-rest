function consultar() {
	$.ajax({
		url: "http://localhost:8080/api/payment-methods",
		type: "GET",

		success: function(response) {
			preencherTabela(response);
		}
	});
}

function cadastrar() {
	const formaPagamentoJson = JSON.stringify({
		"description": $("#campo-descricao").val()
	});

	console.log(formaPagamentoJson);

	$.ajax({
		url: "http://localhost:8080/api/payment-methods",
		type: "POST",
		data: formaPagamentoJson,
		contentType: "application/json",

		success: function(response) {
			alert("Forma de pagamento adicionada!");
			consultar();
		},

		error: function(error) {
			if (error.status === 400) {
				const problem = JSON.parse(error.responseText);
				alert(problem.userMessage);
			} else {
				alert("Erro ao cadastrar forma de pagamento!");
			}
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
$("#btn-cadastrar").click(cadastrar);
