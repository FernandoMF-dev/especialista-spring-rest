package br.com.colatina.fmf.algafood.service.infrastructure.service.storage;

import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {
	@Value("${algafood.storage.local.file-directory}")
	private Path fileDirectory;

	@Override
	public void store(FileStorageService.NewFile newFile) {
		try {
			Path filePath = getFilePath(newFile.getFileName());
			FileCopyUtils.copy(newFile.getInputStream(), Files.newOutputStream(filePath));
		} catch (Exception e) {
			throw new StorageException("infrastructure.error.storage.store", e);
		}
	}

	@Override
	public void remove(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			Files.deleteIfExists(filePath);
		} catch (Exception e) {
			throw new StorageException("infrastructure.error.storage.remove", e);
		}
	}

	private Path getFilePath(String fileName) {
		return fileDirectory.resolve(Path.of(fileName));
	}
}
