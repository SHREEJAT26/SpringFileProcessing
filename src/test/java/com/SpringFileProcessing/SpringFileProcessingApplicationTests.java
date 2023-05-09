package com.SpringFileProcessing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@SpringBootTest
class SpringFileProcessingApplicationTests
{
	private static final String DOWNLOADS_FILE_DIRECTORY = "C:\\Users\\shrthudh\\IdeaProjects\\FileDirectory\\Downloads\\";
	private static final String FILE_UPLOAD_URL = "http://localhost:8080/upload/";
	private static final String FILE_DOWNLOAD_URL = "http://localhost:8080/download/";
	@Autowired
	RestTemplate restTemplate;

	@Test
	void testUpload()
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new ClassPathResource("Shreeja.jpeg"));

		HttpEntity<MultiValueMap<String,Object>> httpEntity = new HttpEntity<>(body,headers);

		ResponseEntity<Boolean> response = restTemplate.postForEntity(FILE_UPLOAD_URL, httpEntity,Boolean.class);
		System.out.println(response.getBody());
	}

	@Test
	void testDownload() throws IOException
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		String fileName = "Shreeja.jpeg";

		ResponseEntity<byte[]> response = restTemplate.exchange(FILE_DOWNLOAD_URL+fileName,HttpMethod.GET,httpEntity,byte[].class);

		Files.write(Paths.get(DOWNLOADS_FILE_DIRECTORY +fileName),response.getBody());
	}



}
