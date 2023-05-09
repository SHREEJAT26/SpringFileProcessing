package com.SpringFileProcessing.Controller;

import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
public class FileController
{
    @Value("${upload.directory}")
    String UPLOAD_DIRECTORY;

    @Value("${download.directory}")
    String DOWNLOAD_DIRECTORY;

    @PostMapping("/upload")
    public Boolean upload(@RequestParam("file") MultipartFile file) throws IOException
    {
        file.transferTo(new File(UPLOAD_DIRECTORY+file.getOriginalFilename()));
        return true;
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> download(@PathVariable("fileName") String fileName) throws IOException
    {
        byte[] fileData = Files.readAllBytes(new File(UPLOAD_DIRECTORY+fileName).toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<byte[]>(fileData,headers,HttpStatus.OK);
    }

}
