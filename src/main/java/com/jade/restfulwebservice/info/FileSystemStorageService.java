package com.jade.restfulwebservice.info;

import com.jade.restfulwebservice.util.FileuploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    @Override
    public  void init(){
        try{
            Files.createDirectories(Paths.get(uploadPath));
        }catch (IOException e){
            throw new RuntimeException("Could not create upload folder!");
        }
    }

    @Override
    public void store(MultipartFile multipartFile) throws Exception {
        try{
            if(multipartFile.isEmpty()){
                throw new Exception("ERROR : File is empty.");
            }
            Path root = Paths.get(uploadPath);

            if(!Files.exists(root)){
                init();
            }
            try(InputStream inputStream = multipartFile.getInputStream()){
                Files.copy(inputStream, root.resolve(FileuploadUtil.getUniqueFileName(multipartFile.getOriginalFilename())),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }catch (Exception e){
            throw new RuntimeException("Cound not store the file. ERROR : "+e.getMessage());
        }
    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
