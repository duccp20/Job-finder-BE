package com.example.jobfinder.controller;

import java.io.IOException;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.constant.PageDefault;
import com.example.jobfinder.data.dto.request.company.CompanyDTO;
import com.example.jobfinder.service.CompanyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.COMPANY)
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("")
    public ResponseEntity<?> findAllActive(
            @RequestParam(defaultValue = PageDefault.NO) int no,
            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.companyService.findAllActive(no, limit));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> findAllByNameLike(
            @PathVariable String name,
            @RequestParam(defaultValue = PageDefault.NO) int no,
            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.companyService.findAllByNameLike(name, no, limit));
    }

    @PostMapping(
            value = "",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(
            @Valid @RequestPart CompanyDTO companyDTO,
            @RequestPart(name = "fileLogo", required = false) MultipartFile fileLogo)
            throws IOException {
        return new ResponseEntity<>(companyService.create(companyDTO, fileLogo), HttpStatus.CREATED);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    public ResponseEntity<?> update(
            @PathVariable long id,
            @Valid @RequestPart CompanyDTO companyDTO,
            @RequestPart(name = "fileLogo", required = false) MultipartFile fileLogo) {
        try {
            return ResponseEntity.ok(this.companyService.update(id, companyDTO, fileLogo));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        return ResponseEntity.ok(this.companyService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        this.companyService.deleteById(id);
        return ResponseEntity.ok("DELETED");
    }
}
