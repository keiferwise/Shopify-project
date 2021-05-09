package com.shopifychal.imagerepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.MinioException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
@Controller
public class ImageController {
	@Autowired
	private ImageCrudRepo imageRepository;

	@GetMapping("/images")
	public String images(Model model) {
		MinioClientFactory mcf = new MinioClientFactory();
		MinioClient mc = null;
		mc = mcf.getClient();
		ArrayList<Image> allImages = (ArrayList<Image>) imageRepository.findAll();
		for (Image i : allImages) {
			System.out.println(i.getPath());
		}
		//System.out.println(allImages.getClass());
		String url=null;
		/*

		}
		 */
		System.out.println(url);
		model.addAttribute("allImages",allImages);
		return "images";
	}
	//@GetMapping("/view/{id}")
	//@GetMapping("/edit/{id}")
	//@GetMapping("/delete/{id}")

	@PostMapping("/create")
	public String newImage(@RequestParam("file") MultipartFile file) {
		MinioClientFactory mcf = new MinioClientFactory();
		MinioClient mc = null;
		mc = mcf.getClient();
		Image img = new Image();
		img.setName(file.getOriginalFilename());
		img.setPath(file.getOriginalFilename());
		imageRepository.save(img);

		try {
			InputStream inputStream =  new BufferedInputStream(file.getInputStream());
			mc.putObject(
					PutObjectArgs.builder().bucket("images").object(file.getOriginalFilename()).stream(
							inputStream, -1, 10485760)
					.contentType("image/jpg")
					.build());

		} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
				| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
				| IllegalArgumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "images";
	}

	@GetMapping("/create")
	public String create() {


		return "create";
	}
	@GetMapping("/image/{id}")
	public String view(@PathVariable("id") Long imageId, Model model) {

		MinioClientFactory mcf = new MinioClientFactory();
		MinioClient mc = null;
		mc = mcf.getClient();
		Optional<Image> img = imageRepository.findById(imageId);
		//System.out.println(img.get().getName());
		if(img.isEmpty()) {
			return "images";
		}
		else {
			String imgName=img.get().getName();
			System.out.println(imgName);
			model.addAttribute("img", img.get());
			String url=null;
			try {
				url = mc.getPresignedObjectUrl(
						GetPresignedObjectUrlArgs.builder()
						.method(Method.GET)
						.bucket("images")
						.object(imgName)
						.expiry(24 * 60 * 60)
						.build());
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | XmlParserException | ServerException
					| IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(url);
			model.addAttribute("url", url);


			return "image";
		}
	}
	@GetMapping("/delete/{id}")
	public String deleteImage(@PathVariable("id") Long imageId, Model model) {
		MinioClientFactory mcf = new MinioClientFactory();
		MinioClient mc = null;
		mc = mcf.getClient();
		Optional<Image> img = imageRepository.findById(imageId);
		
		if(!img.isEmpty()) {
			
			try {
				mc.removeObject(
					     RemoveObjectArgs.builder().bucket("images").object(img.get().getName()).build());
			} catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
					| InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
					| IllegalArgumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			imageRepository.deleteById(imageId);

		}


		
		return "images";
		
	}
	
}
