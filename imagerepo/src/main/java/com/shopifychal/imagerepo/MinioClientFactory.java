package com.shopifychal.imagerepo;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MinioClientFactory {
  public MinioClient getClient() {
    try {
      MinioClient minioClient =
          MinioClient.builder()
              .endpoint("localhost",9000, false)
              .credentials("minioadmin", "minioadmin")
              .build();

      boolean found = false;
	try {
		found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("images").build());
	} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException | IOException e) {
		e.printStackTrace();
	}
      if (!found) {
        try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket("images").build());
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
      } 
  	return minioClient;
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
      return null;
    }

  }
}
