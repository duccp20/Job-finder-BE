package com.example.jobfinder.utils.common;

public interface UpdateFile {


	void update(FileUpload fileUp);
	void uploadCV(FileUpload fileUpload);
	void uploadCVApplication(FileUpload fileUpload);
	void uploadExcel(FileUpload fileUp);
	void deleteFile(String url);
}
