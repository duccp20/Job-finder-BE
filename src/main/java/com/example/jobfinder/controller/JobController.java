package com.example.jobfinder.controller;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.constant.PageDefault;
import com.example.jobfinder.data.dto.request.job.JobCreationDTO;
import com.example.jobfinder.data.dto.request.job.JobDTO;
import com.example.jobfinder.data.dto.request.job.JobFilterDTO;
import com.example.jobfinder.service.JobService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(ApiURL.JOB)
public class JobController {
    @Autowired
    private JobService jobService;


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody JobCreationDTO jobCreationDTO) {
        return new ResponseEntity<>(this.jobService.create(jobCreationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/active")
    public ResponseEntity<?> findAllActive(@RequestParam(defaultValue = PageDefault.NO) int no,
                                           @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        try {
            return ResponseEntity.ok(jobService.findAllActive(no, limit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(this.jobService.findById(id));
    }



    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody JobDTO jobDTO) {
        return ResponseEntity.ok(this.jobService.update(id, jobDTO));
    }


    @GetMapping("/filter") // filter in home page
    public ResponseEntity<?> filter(@RequestParam(defaultValue = PageDefault.NO) int no,
                                    @RequestParam(defaultValue = PageDefault.LIMIT) int limit,
                                    @RequestParam(required = false) List<String> schedule,
                                    @RequestParam(required = false) List<String> position,
                                    @RequestParam(required = false) List<String> major,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) String provinceName) {
        JobFilterDTO jobFilterDTO = new JobFilterDTO(name, position, schedule, major, provinceName);
      try {
          return ResponseEntity.ok(this.jobService.filterJob(jobFilterDTO, no, limit));
      } catch (Exception e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }

    }



    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @PutMapping("/replicate/{id}")
    public ResponseEntity<?> replicate(@PathVariable long id, @RequestBody JobDTO jobDTO) {
        try {
            return ResponseEntity.ok(this.jobService.replicate(id, jobDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @PutMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            return ResponseEntity.ok(this.jobService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    };

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @PutMapping("/disable/{id}")
    public ResponseEntity<?> disable(@PathVariable long id) {
        try {
            return ResponseEntity.ok(this.jobService.disable(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    };


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/countJobAndApplicationByMonth")
    public ResponseEntity<?> countJobAndApplicationByMonth() {
        return ResponseEntity.ok(this.jobService.countByMonth());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/countJobAndApplicationByYear")
    public ResponseEntity<?> countJobAndApplicationByYear() {
        return ResponseEntity.ok(this.jobService.countByYear());
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/active/hr/company")
    public ResponseEntity<?> findAllActiveByCompanyIdShowForHr(
            @RequestParam(defaultValue = PageDefault.NO) int no,
            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.jobService.findAllActiveByCompanyIdShowForHr(no, limit));
    }
//
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/disable/hr/company")
    public ResponseEntity<?> findAllDisableByCompanyIdShowForHr(
            @RequestParam(defaultValue = PageDefault.NO) int no,
            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.jobService.findAllDisableByCompanyIdShowForHr(no, limit));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/hr/company")
    public ResponseEntity<?> findAllByCompanyIdShowForHr(
            @RequestParam(defaultValue = PageDefault.NO) int no,
            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.jobService.findAllByCompanyIdShowForHr(no, limit));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/active/hr/company/count")
    public ResponseEntity<?> countAllActiveByCompanyId() {
        return ResponseEntity.ok(this.jobService.countAllActiveByCompanyIdShowForHr());
    }
//
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/disable/hr/company/count")
    public ResponseEntity<?> countAllDisableByCompanyId() {
        return ResponseEntity.ok(this.jobService.countAllDisableByCompanyIdShowForHr());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/hr/company/count")
    public ResponseEntity<?> countAllByCompanyIdShowForHr() {
        return ResponseEntity.ok(this.jobService.countAllByCompanyId());
    }

    // Get job by company for Candidate and Guest
    @GetMapping("/active/company/{id}")
    public ResponseEntity<?> findAllActiveByCompanyId(@PathVariable("id") long companyId,
                                                      @RequestParam(defaultValue = PageDefault.NO) int no,
                                                      @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.jobService.findAllActiveByCompanyId(companyId, no, limit));
    }


}
