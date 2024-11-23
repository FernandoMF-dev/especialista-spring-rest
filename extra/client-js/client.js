const apiUrl = "http://localhost:8080/api/v1/payment-methods";

function tratarErroDeRequisicao(error, mensagemPadrao) {
	if (error.status >= 400 && error.status <= 499) {
		var problem = JSON.parse(error.responseText);
		alert(problem.userMessage);
	} else {
		alert(mensagemPadrao);
	}
}

function consultar() {
	$.ajax({
		url: apiUrl,
		type: "GET",

		success: function (response) {
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
		url: apiUrl,
		type: "POST",
		data: formaPagamentoJson,
		contentType: "application/json",

		success: function (response) {
			alert("Forma de pagamento adicionada!");
			consultar();
		},

		error: function (error) {
			tratarErroDeRequisicao(error, "Erro ao cadastrar forma de pagamento!");
		}
	});
}

function excluir(formaPagamento) {
	const url = `${apiUrl}/${formaPagamento.id}`;

	$.ajax({
		url: url,
		type: "DELETE",

		success: function (response) {
			consultar();
			alert("Forma de pagamento removida!");
		},

		error: function (error) {
			tratarErroDeRequisicao(error, "Erro ao remover forma de pagamento!");
		}
	});
}

function preencherTabela(formasPagamento) {
	$("#tabela tbody tr").remove();

	$.each(formasPagamento, function (i, formaPagamento) {
		const linha = $("<tr>");

		const linkAcao = $("<a href='#'>")
			.text("Excluir")
			.click(function (event) {
				event.preventDefault();
				excluir(formaPagamento);
			});

		linha.append(
			$("<td>").text(formaPagamento.id),
			$("<td>").text(formaPagamento.description),
			$("<td>").append(linkAcao)
		);

		linha.appendTo("#tabela");
	});
}


$("#btn-consultar").click(consultar);
$("#btn-cadastrar").click(cadastrar);
