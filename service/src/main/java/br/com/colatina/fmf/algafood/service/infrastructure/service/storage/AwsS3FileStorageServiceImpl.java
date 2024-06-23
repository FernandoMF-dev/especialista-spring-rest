package br.com.colatina.fmf.algafood.service.infrastructure.service.storage;

import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AwsS3FileStorageServiceImpl implements FileStorageService {
	private final AmazonS3 amazonS3;

	@Override
	public InputStream getFile(String fileName) {
		// TODO: Implement this method
		return null;
	}

	@Override
	public void store(NewFile newFile) {
		// TODO: Implement this method
	}

	@Override
	public void remove(String fileName) {
		// TODO: Implement this method
	}
}
