package com.krouba.Cedounga.upload;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    public UploadedFile uploadToDb(MultipartFile file);
    public UploadedFile downloadFile(String fileId);
}
