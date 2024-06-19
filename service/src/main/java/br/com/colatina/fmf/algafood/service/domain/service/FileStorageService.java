package br.com.colatina.fmf.algafood.service.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FileStorageService {
	void store(NewFile newFile);

	default String generateFileName(String fileName) {
		return String.format("%s_%s", UUID.randomUUID(), fileName);
	}

	@Getter
	@Builder
	class NewFile {
		private String fileName;
		private InputStream inputStream;
	}
}
