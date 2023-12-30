package com.example.jobfinder.controller.candidate;

import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.constant.PageDefault;
import com.example.jobfinder.service.JobCareService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiURL.CANDIDATE_JOB_CARE)
@CrossOrigin(origins = "*", maxAge = 3600)
public class CandidateJobCareController {
    @Autowired
    private JobCareService jobCareService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.jobCareService.findAll());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(this.jobCareService.findById(id));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/job-save")
    public ResponseEntity<?> findAllJobSave(){
        return ResponseEntity.ok(this.jobCareService.findJobSaveOfCandidateID());
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/candidate/{id}")
    public ResponseEntity<?> findAllByCandidateId(@PathVariable("id") long candidateId,
                                                  @RequestParam(defaultValue = PageDefault.NO) int no,
                                                  @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.jobCareService.findAllByCandidateId(candidateId, no, limit));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestParam("id") long idJob) {
        return ResponseEntity.ok(this.jobCareService.create(idJob));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") long idJobCare) {
        return ResponseEntity.ok(this.jobCareService.deleteById(idJobCare));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/check")
    public boolean checkCandidateJobCare (@RequestParam("idJob") int idJob) {
        return this.jobCareService.checkCandidateApplication(idJob);
    }
}


