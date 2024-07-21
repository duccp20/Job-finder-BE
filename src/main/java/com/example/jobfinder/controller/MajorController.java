package com.example.jobfinder.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.major.MajorDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.service.MajorService;

@RestController
@RequestMapping(ApiURL.MAJOR)
@CrossOrigin(origins = "*", maxAge = 3600)
public class MajorController {

    @Autowired
    private MajorService majorService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .httpCode(HttpServletResponse.SC_OK)
                        .message("Get all majors successfully")
                        .data(this.majorService.findAll())
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody MajorDTO majorDTO) {
        return ResponseEntity.ok(this.majorService.create(majorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.majorService.deleteById(id));
    }
}
