package com.example.jobfinder.utils.common;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileUpload {
    private MultipartFile file;
    private String output;
}
