package br.com.colatina.fmf.algafood.service.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Set;

public interface EmailSendService {
	void send(Email email);

	@Getter
	@Builder
	class Email {
		@NonNull
		private String subject;
		@NonNull
		private String body;
		@Singular
		private Set<String> recipients;
	}
}
