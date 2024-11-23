const config = {
	clientId: "fmf-algafood-analytics",
	clientSecret: "analytics123",
	authorizeUrl: "http://localhost:8081/oauth/authorize",
	tokenUrl: "http://localhost:8081/oauth/token",
	callbackUrl: "http://localhost:63342",
	apiServiceUrl: "http://localhost:8080/api/v1/payment-methods"
};

let accessToken = "";

function generateCodeVerifier() {
	const codeVerifier = generateRandomString(128);
	localStorage.setItem("codeVerifier", codeVerifier);
	return codeVerifier;
}

function generateRandomString(length) {
	const possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	let text = "";

	for (let i = 0; i < length; i++) {
		text += possible.charAt(Math.floor(Math.random() * possible.length));
	}

	return text;
}

function generateCodeChallenge(codeVerifier) {
	return base64URL(CryptoJS.SHA256(codeVerifier));
}

function getCodeVerifier() {
	return localStorage.getItem("codeVerifier");
}

function base64URL(string) {
	return string.toString(CryptoJS.enc.Base64)
		.replace(/=/g, '')
		.replace(/\+/g, '-')
		.replace(/\//g, '_');
}

function consultar() {
	alert("Consultando recurso com access token " + accessToken);

	$.ajax({
		url: config.apiServiceUrl,
		type: "get",

		beforeSend: function (request) {
			request.setRequestHeader("Authorization", "Bearer " + accessToken);
		},

		success: function (response) {
			const json = JSON.stringify(response);
			$("#resultado").text(json);
		},

		error: function (error) {
			alert("Erro ao consultar recurso");
		}
	});
}

function gerarAccessToken(code) {
	alert("Gerando access token com code " + code);

	const codeVerifier = getCodeVerifier();

	let params = new URLSearchParams();
	params.append("grant_type", "authorization_code");
	params.append("code", code);
	params.append("redirect_uri", config.callbackUrl);
	params.append("client_id", config.clientId);
	params.append("code_verifier", codeVerifier);

	$.ajax({
		url: config.tokenUrl,
		type: "post",
		data: params.toString(),
		contentType: "application/x-www-form-urlencoded",

		success: function (response) {
			accessToken = response.access_token;

			alert("Access token gerado: " + accessToken);
		},

		error: function (error) {
			alert("Erro ao gerar access key");
		}
	});
}

function login() {
	const codeVerifier = generateCodeVerifier();
	const codeChallenge = generateCodeChallenge(codeVerifier);

	window.location.href = `${config.authorizeUrl}?response_type=code&client_id=${config.clientId}&redirect_uri=${config.callbackUrl}&code_challenge_method=s256&code_challenge=${codeChallenge}`;
}

$(document).ready(function () {
	const params = new URLSearchParams(window.location.search);
	const code = params.get("code");
	if (code) {
		// window.history.replaceState(null, null, "/");
		gerarAccessToken(code);
	}
});

$("#btn-consultar").click(consultar);
$("#btn-login").click(login);
