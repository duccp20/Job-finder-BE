// package com.example.jobfinder.controller;
//
// import com.example.jobfinder.constant.ApiURL;
// import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
// import com.example.jobfinder.data.dto.response.ResponseMessage;
// import com.example.jobfinder.exception.AccessDeniedException;
// import com.example.jobfinder.service.FileService;
// import com.example.jobfinder.service.JsonReaderService;
// import com.example.jobfinder.utils.common.FileUpload;
// import com.example.jobfinder.utils.common.UpdateFile;
// import jakarta.validation.Valid;
// import lombok.RequiredArgsConstructor;
// import org.apache.commons.io.IOUtils;
// import org.apache.tika.Tika;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.core.io.ByteArrayResource;
// import org.springframework.core.io.Resource;
// import org.springframework.core.io.ResourceLoader;
// import org.springframework.http.*;
// import org.springframework.stereotype.Component;
// import org.springframework.web.bind.annotation.*;
// import org.springframework.web.client.RestTemplate;
// import org.springframework.web.multipart.MultipartFile;
//
// import java.io.File;
// import java.io.IOException;
// import java.io.InputStream;
// import java.net.URLConnection;
// import java.nio.file.FileSystems;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.Base64;
// import java.util.Objects;
//
// @CrossOrigin(origins = "*", maxAge = 3600)
// @RestController
// @RequestMapping(ApiURL.FILE)
// @RequiredArgsConstructor
// @Component
// public class FileController {
//    @Autowired
//    private FileService fileService;
//
//    @Autowired
//    private UpdateFile updateFile;
//
//    @Autowired
//    private JsonReaderService<Object> jsonReaderService;
//
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
//        return ResponseEntity.status(HttpStatus.OK).body(fileService.uploadFile(file));
//    }
//
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
//        Resource resource = fileService.downloadFile(fileName);
//        String originalFilename = resource.getFilename().split("_", 2)[1];
//        String mediaType = URLConnection.guessContentTypeFromName(originalFilename);
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(mediaType))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFilename + "\"")
//                .body(resource);
//    }
//
//
//    @GetMapping("/display/{fileName}")  // view on web
//    public ResponseEntity<byte[]> displayFile(@PathVariable String fileName) throws IOException {
//        Resource resource = fileService.downloadFile(fileName);
//        String originalFilename = resource.getFilename().split("_", 2)[1];
//
//        Tika tika = new Tika();
//        String mediaType = tika.detect(originalFilename);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(mediaType));
//
//        headers.setContentDisposition(ContentDisposition.inline().filename(originalFilename).build());
//
//        InputStream inputStream = resource.getInputStream();
//        byte[] fileBytes = IOUtils.toByteArray(inputStream);
//        inputStream.close();
//
//        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
//    }
//
//    @GetMapping("/raw/{fileName}") // Returns file as Base64 string
//    public ResponseEntity<Object> displayRawFile(@PathVariable String fileName) {
//
//        try {
////            String fileUrl =
// "https://firebasestorage.googleapis.com/v0/b/job-worked.appspot.com/o/pdfs%2F976563fc-2cb3-440e-b58e-c5b89ad47b3f.pdf?alt=media";
////            ResponseEntity<byte[]> response = restTemplate.exchange(
////                    fileUrl,
////                    HttpMethod.GET,
////                    new HttpEntity<>(new HttpHeaders()),
////                    byte[].class
////            );
////
////            ByteArrayResource resource = new ByteArrayResource(Objects.requireNonNull(response.getBody()));
//            return ResponseEntity.ok(updateFile.downloadCV(fileName));
////            return ResponseEntity.ok()
////                    .contentType(MediaType.APPLICATION_PDF)
////                    .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//
//
//    }
//
//
//
//    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        return ResponseEntity.status(HttpStatus.OK).body(updateFile.uploadImage(multipartFile));
//    }
//
//    @PostMapping(value = "/upload/cv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> uploadCV(@RequestParam("file") MultipartFile multipartFile) throws IOException {
//        try {
//            return ResponseEntity.status(HttpStatus.OK).body(updateFile.uploadCV(multipartFile));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }
// }
