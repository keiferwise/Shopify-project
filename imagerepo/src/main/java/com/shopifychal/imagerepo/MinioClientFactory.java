package com.shopifychal.imagerepo;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
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

      // Make 'images' bucket if not exist.
      boolean found = false;
	try {
		found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("images").build());
	} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException | IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      if (!found) {
        // Make a new bucket called 'images'.
        try {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket("images").build());
		} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      } else {
        System.out.println("Bucket 'images' already exists.");
      }
  	return minioClient;
    } catch (MinioException e) {
      System.out.println("Error occurred: " + e);
      System.out.println("HTTP trace: " + e.httpTrace());
      return null;
    }

  }
}

/*
minioClient.uploadObject(
          UploadObjectArgs.builder()
              .bucket("asiatrip")
              .object("asiaphotos-2015.zip")
              .filename("/home/user/Photos/asiaphotos.zip")
              .build());
      System.out.println(
          "'/home/user/Photos/asiaphotos.zip' is successfully uploaded as "
              + "object 'asiaphotos-2015.zip' to bucket 'asiatrip'.");
*/