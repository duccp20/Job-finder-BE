package com.example.jobfinder.controller;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.schedule.ScheduleDTO;
import com.example.jobfinder.data.dto.response.ApiResponse;
import com.example.jobfinder.service.ScheduleService;

@RestController
@RequestMapping(ApiURL.SCHEDULE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .httpCode(HttpServletResponse.SC_OK)
                        .message("Get all majors successfully")
                        .data(this.scheduleService.findAll())
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(this.scheduleService.create(scheduleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.scheduleService.deleteById(id));
    }
}
