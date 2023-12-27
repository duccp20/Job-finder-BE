package com.example.jobfinder.controller;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.exception.AccessDeniedException;
import com.example.jobfinder.service.FileService;
import com.example.jobfinder.service.JsonReaderService;
import com.example.jobfinder.utils.common.FileUpload;
import com.example.jobfinder.utils.common.UpdateFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.FILE)
public class FileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private UpdateFile updateFile;

    @Autowired
    private JsonReaderService<Object> jsonReaderService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(fileService.uploadFile(file));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.downloadFile(fileName);
        String originalFilename = resource.getFilename().split("_", 2)[1];
        String mediaType = URLConnection.guessContentTypeFromName(originalFilename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mediaType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + originalFilename + "\"")
                .body(resource);
    }


    @GetMapping("/display/{fileName}")  // view on web
    public ResponseEntity<byte[]> displayFile(@PathVariable String fileName) throws IOException {
        Resource resource = fileService.downloadFile(fileName);
        String originalFilename = resource.getFilename().split("_", 2)[1];

        Tika tika = new Tika();
        String mediaType = tika.detect(originalFilename);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(mediaType));

        headers.setContentDisposition(ContentDisposition.inline().filename(originalFilename).build());

        InputStream inputStream = resource.getInputStream();
        byte[] fileBytes = IOUtils.toByteArray(inputStream);
        inputStream.close();

        return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/raw/{fileName}") // Returns file as Base64 string
    public ResponseEntity<String> displayRawFile(@PathVariable String fileName) {
        try {
            // Correctly construct the path to the files directory.
            // If your path/files directory is inside the project directory and not in src/main/resources,
            // you can construct the path relative to the project directory like this:
            Path currentRelativePath = Paths.get("");
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);
            Path filePath = Paths.get(s, "path", "files", fileName);

            if (!Files.exists(filePath)) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileData = Files.readAllBytes(filePath);
            String base64Data = Base64.getEncoder().encodeToString(fileData);
            String dataUri = "data:application/pdf;base64," + base64Data;

            return ResponseEntity.ok(dataUri);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Không thể đọc file: " + fileName);
        }
    }



    @PostMapping(value = "/upload/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(updateFile.uploadImage(multipartFile));
    }

}
