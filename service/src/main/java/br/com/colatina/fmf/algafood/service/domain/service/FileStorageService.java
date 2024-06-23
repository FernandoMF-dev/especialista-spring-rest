package br.com.colatina.fmf.algafood.service.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FileStorageService {
	InputStream getFile(String fileName);

	void store(NewFile newFile);

	void remove(String fileName);

	default String generateFileName(String fileName) {
		return String.format("%s_%s", UUID.randomUUID(), fileName);
	}

	@Getter
	@Builder
	class NewFile {
		private String fileName;
		private String contentType;
		private InputStream inputStream;
	}
}
