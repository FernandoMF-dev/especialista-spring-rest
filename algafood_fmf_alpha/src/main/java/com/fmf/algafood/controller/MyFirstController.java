package com.fmf.algafood.controller;

import com.fmf.algafood.model.Cliente;
import com.fmf.algafood.service.AtivacaoClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyFirstController {
	private final AtivacaoClienteService ativacaoClienteService;

	public MyFirstController(AtivacaoClienteService ativacaoClienteService) {
		this.ativacaoClienteService = ativacaoClienteService;

		System.out.println("MeuPrimeiroController: " + ativacaoClienteService);
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		Cliente joao = new Cliente("João", "joao@xyz.com", "3499998888");

		ativacaoClienteService.ativar(joao);

		return "Hello!";
	}
}
