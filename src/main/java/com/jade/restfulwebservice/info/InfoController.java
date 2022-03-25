package com.jade.restfulwebservice.info;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/info")
public class InfoController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(MultipartFile multipartFile) throws IllegalStateException, IOException{
        if(!multipartFile.isEmpty()){
            log.debug("file org name = " + multipartFile.getOriginalFilename());
            log.debug("file content type = " + multipartFile.getContentType());
            multipartFile.transferTo((new File(multipartFile.getOriginalFilename())));
        }
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile multipartFile) throws Exception {
        storageService.store(multipartFile);
        return new ResponseEntity<>("",HttpStatus.OK);
    }
}
