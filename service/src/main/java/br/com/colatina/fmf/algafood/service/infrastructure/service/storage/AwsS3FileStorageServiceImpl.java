package br.com.colatina.fmf.algafood.service.infrastructure.service.storage;

import br.com.colatina.fmf.algafood.service.core.storage.StorageProperties;
import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AwsS3FileStorageServiceImpl implements FileStorageService {
	private final StorageProperties storageProperties;
	private final AmazonS3 amazonS3;

	@Override
	public InputStream getFile(String fileName) {
		// TODO: Implement this method
		return null;
	}

	@Override
	public void store(NewFile newFile) {
		try {
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newFile.getContentType());

			var putObjectRequest = new com.amazonaws.services.s3.model.PutObjectRequest(
					storageProperties.getAwsS3().getBucket(),
					getFilePath(newFile.getFileName()),
					newFile.getInputStream(),
					objectMetadata
			).withCannedAcl(CannedAccessControlList.PublicRead);

			amazonS3.putObject(putObjectRequest);
		} catch (Exception e) {
			throw new StorageException("infrastructure.error.storage.store", e);
		}
	}

	@Override
	public void remove(String fileName) {
		// TODO: Implement this method
	}

	private String getFilePath(String fileName) {
		return storageProperties.getAwsS3().getFileDirectory() + fileName;
	}
}
