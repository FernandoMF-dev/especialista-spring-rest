package br.com.colatina.fmf.algafood.service.infrastructure.service.storage;

import br.com.colatina.fmf.algafood.service.core.storage.StorageProperties;
import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

//@Service
@RequiredArgsConstructor
public class LocalFileStorageServiceImpl implements FileStorageService {
	private final StorageProperties storageProperties;

	@Override
	public RestoredFile restoreFile(String fileName) {
		try {
			Path filePath = getFilePath(fileName);
			InputStream inputStream = Files.newInputStream(filePath);

			return RestoredFile.builder().inputStream(inputStream).build();
		} catch (Exception e) {
			throw new StorageException("infrastructure.error.storage.get-file", e);
		}
	}

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
		return storageProperties.getLocal().getFileDirectory()
				.resolve(Path.of(fileName));
	}
}
