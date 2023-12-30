package com.example.jobfinder.utils.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface UpdateFile {


	String uploadImage(MultipartFile multipartFile) throws IOException;
	String uploadCV(MultipartFile fileUpload) throws IOException;
//	void uploadCVApplication(FileUpload fileUpload);
//	void uploadExcel(FileUpload fileUp);
//	void deleteFile(String url);
}
