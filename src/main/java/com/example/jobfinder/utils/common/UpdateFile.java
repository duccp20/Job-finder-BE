package com.example.jobfinder.utils.common;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UpdateFile {

    String uploadImage(MultipartFile multipartFile) throws IOException;

    String uploadCV(MultipartFile fileUpload) throws IOException;

    Object downloadCV(String fileName) throws IOException;
    //	void uploadCVApplication(FileUpload fileUpload);
    //	void uploadExcel(FileUpload fileUp);
    //	void deleteFile(String url);
}
