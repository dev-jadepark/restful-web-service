package com.jade.restfulwebservice.fileupload;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/FileUpload")
public class FileUploadController {

    @Autowired
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile multipartFile) throws Exception {
        storageService.store(multipartFile);
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("/serveFile")
    public ResponseEntity<Resource> serveFile(@RequestParam(value = "filename") String filename){
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);

    }

    @PostMapping("/deleteAll")
    public ResponseEntity<String> deleteAll(){
        storageService.deleteAll();
        return new ResponseEntity<>("",HttpStatus.OK);
    }

    @GetMapping("/getListFiles")
    public ResponseEntity<List<FileData>> getListFiles(){
        List<FileData> fileList = storageService.loadAll()
                .map(path -> {
                    FileData fileData = new FileData();
                    String filename = path.getFileName().toString();
                    fileData.setFilename(filename);
                    fileData.setUrl(MvcUriComponentsBuilder
                            .fromMethodName(FileUploadController.class,"serveFile", filename).build().toString());
                    try {
                        fileData.setSize(Files.size(path));
                    }catch (IOException e){
                        log.error(e.getMessage());
                    }
                    return fileData;
                }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(fileList);
    }

}
