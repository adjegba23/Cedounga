package com.krouba.Cedounga.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements  FileUploadService{
    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Override
    public UploadedFile uploadToDb(MultipartFile file) {

        UploadedFile uploadedFile = new UploadedFile();
        try {
            uploadedFile.setFileData(file.getBytes());
            uploadedFile.setFileType(file.getContentType());
            uploadedFile.setFileName(file.getOriginalFilename());
            UploadedFile uploadedFileToRet = fileUploadRepository.save(uploadedFile);
            return uploadedFileToRet;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public UploadedFile downloadFile(String fileId) {
        UploadedFile uploadedFileToRet = fileUploadRepository.getOne(fileId);
        return uploadedFileToRet;
    }
}
