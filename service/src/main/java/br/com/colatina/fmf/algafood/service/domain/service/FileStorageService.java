package br.com.colatina.fmf.algafood.service.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FileStorageService {
	void store(NewFile newFile);

	@Getter
	@Builder
	class NewFile {
		private String fileName;
		private InputStream inputStream;
	}
}
