package br.com.colatina.fmf.algafood.service.infrastructure.service.storage;

import br.com.colatina.fmf.algafood.service.core.storage.StorageProperties;
import br.com.colatina.fmf.algafood.service.domain.service.FileStorageService;
import br.com.colatina.fmf.algafood.service.infrastructure.exceptions.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import java.net.URL;

@RequiredArgsConstructor
public class AwsS3FileStorageServiceImpl implements FileStorageService {
	private final StorageProperties storageProperties;
	private final AmazonS3 amazonS3;

	@Override
	public RestoredFile restoreFile(String fileName) {
		String filePath = getFilePath(fileName);
		String bucket = storageProperties.getAwsS3().getBucket();
		URL url = amazonS3.getUrl(bucket, filePath);

		return RestoredFile.builder().url(url.toString()).build();
	}

	@Override
	public void store(NewFile newFile) {
		try {
			var objectMetadata = new ObjectMetadata();
			objectMetadata.setContentType(newFile.getContentType());

			var putObjectRequest = new PutObjectRequest(
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
		try {
			String bucket = storageProperties.getAwsS3().getBucket();
			String filePath = getFilePath(fileName);

			var deleteObjectRequest = new DeleteObjectRequest(bucket, filePath);
			amazonS3.deleteObject(deleteObjectRequest);
		} catch (Exception e) {
			throw new StorageException("infrastructure.error.storage.remove", e);
		}
	}

	private String getFilePath(String fileName) {
		return storageProperties.getAwsS3().getFileDirectory() + fileName;
	}
}
