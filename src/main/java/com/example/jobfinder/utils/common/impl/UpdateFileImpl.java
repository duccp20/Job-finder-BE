package com.example.jobfinder.utils.common.impl;

import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

import com.example.jobfinder.utils.common.UpdateFile;
import com.google.auth.Credentials;
import com.google.cloud.storage.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.web.multipart.MultipartFile;


@Component
@Slf4j
public class UpdateFileImpl implements UpdateFile {


    @Value("${url.firebase.bucket}")
    String bucketName;
    @Value("${url.firebase.folder}")
    String linkFolder;
    public final static String FOLDER_NAME = "images/";


    private String uploadFile(File file, String fileName) throws IOException {
        String filePath = FOLDER_NAME + fileName;
        BlobId blobId = BlobId.of(bucketName, filePath); // Replace with your bucket name
//        BlobId blobId = BlobId.of(bucketName, fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = UpdateFileImpl.class.getClassLoader().getResourceAsStream("job-worked-firebase.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        String DOWNLOAD_URL = linkFolder +bucketName+"/o/images%2F" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "?alt=media";
        String DOWNLOAD_URL = linkFolder + bucketName + "/o/images%2F" + fileName + "?alt=media";
        log.info(DOWNLOAD_URL);

//    https:firebasestorage.googleapis.com/v0/b/job-worked.appspot.com/o/images%2Fadab3deb-4c49-4ce7-ba44-3fbf57d4524a.png?alt=media
        return fileName;
    }

    private String uploadFileCV(File file, String fileName) throws IOException {
        String filePath = "pdfs/" + fileName;
        BlobId blobId = BlobId.of(bucketName, filePath); // Replace with your bucket name
//        BlobId blobId = BlobId.of(bucketName, fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("application/pdf").build();
        InputStream inputStream = UpdateFileImpl.class.getClassLoader().getResourceAsStream("job-worked-firebase.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));
//        String DOWNLOAD_URL = linkFolder +bucketName+"/o/images%2F" + URLEncoder.encode(fileName, StandardCharsets.UTF_8) + "?alt=media";
        String DOWNLOAD_URL = linkFolder + bucketName + "/o/pdfs%2F" + fileName + "?alt=media";
        log.info(DOWNLOAD_URL);

//    https:firebasestorage.googleapis.com/v0/b/job-worked.appspot.com/o/images%2Fadab3deb-4c49-4ce7-ba44-3fbf57d4524a.png?alt=media
        return fileName;
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String URL = this.uploadFile(file, fileName);                                   // to get uploaded file link
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }

    }

    @Override
    public String uploadCV(MultipartFile multipartFile) throws IOException {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName = UUID.randomUUID().toString().concat(this.getExtension(fileName));  // to generated random string values for file name.
            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String URL = this.uploadFileCV(file, fileName);                                   // to get uploaded file link
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "PDF couldn't upload, Something went wrong";
        }

    }
}




//    @Override
//    public void uploadExcel(FileUpload fileUpload) {
// try {
// DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
// fileUpload.setOutput(LocalDateTime.now().format(formatter) +
// fileUpload.getFile().getOriginalFilename().substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));
//
// Credentials credentials = GoogleCredentials.fromStream(new
// ClassPathResource("keystorage.json").getInputStream());
// Storage storage =
// StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//
// String folderName = "Students/";
// BlobId blobId = BlobId.of("r2s",folderName + fileUpload.getOutput());
// BlobInfo blobInfo =
// BlobInfo.newBuilder(blobId).setContentType(fileUpload.getFile().getContentType()).build();
// byte[] arr = fileUpload.getFile().getBytes();
// storage.create(blobInfo, arr);
//
// fileUpload.setOutput("https://storage.googleapis.com/" + bucketName + "/" +
// folderName +fileUpload.getOutput());
// fileUpload.setFile(null);
// } catch (Exception e) {
// e.printStackTrace();
// }
//        try {
//
//            UUID uuid = UUID.randomUUID();
//            fileUpload.setOutput(uuid +
//                    fileUpload.getFile().getOriginalFilename()
//                            .substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));
//
//            String name = fileUpload.getOutput();
//
//            byte[] arr = fileUpload.getFile().getBytes();
//            Bucket bucket = StorageClient.getInstance().bucket();
//            bucket.create(FOLDER_NAME + name, arr, fileUpload.getFile().getContentType());
//
//            fileUpload.setOutput(linkFolder + bucketName + "/o/" + "images%2F"
//                    + name + "?alt=media");
//            fileUpload.setFile(null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public void deleteFile(String fullPath) {
//
//        try {
//            String name = fullPath.substring(fullPath.lastIndexOf("%") + 3);
//            String pathImg = name.substring(0, name.indexOf("?"));
//
//            System.out.println(pathImg + " " + fullPath.lastIndexOf("%"));
//            Bucket bucket = StorageClient.getInstance().bucket(bucketName);
//
//            Blob blob = bucket.get(FOLDER_NAME + pathImg);
//
//            blob.delete();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void uploadCVApplication(FileUpload fileUpload) {
//
//        try {
//
//            UUID uuid = UUID.randomUUID();
//            fileUpload.setOutput(uuid +
//                    fileUpload.getFile().getOriginalFilename()
//                            .substring(fileUpload.getFile().getOriginalFilename().lastIndexOf(".")));
//
//            String name = fileUpload.getOutput();
//
//            byte[] arr = fileUpload.getFile().getBytes();
//            Bucket bucket = StorageClient.getInstance().bucket();
//            bucket.create(FOLDER_NAME + name, arr, fileUpload.getFile().getContentType());
//
//            fileUpload.setOutput(linkFolder + bucketName + "/o/" + "images%2F"
//                    + name + "?alt=media");
//            fileUpload.setFile(null);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

