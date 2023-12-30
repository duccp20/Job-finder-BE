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

    /**
     * Creates a new job care record.
     *
     * @param  idJob  the ID of the job
     * @return        a ResponseEntity containing the result of the creation
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @PostMapping("")
    public ResponseEntity<?> create(@RequestParam("id") long idJob) {
        return ResponseEntity.ok(this.jobCareService.create(idJob));
    }

    /**
     * Retrieves a CANDIDATE JOB CARE by its ID.
     *
     * @param  id the ID of the CANDIDATE JOB CARE
     * @return    a ResponseEntity containing the CANDIDATE JOB CARE
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        return ResponseEntity.ok(this.jobCareService.findById(id));
    }
    /**
     * Retrieves all jobs by candidate ID.
     *
     * @param  candidateId  the ID of the candidate
     * @param  no           the page number (default is 0)
     * @param  limit        the number of items per page (default is 10)
     * @return              a ResponseEntity containing the list of jobs
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/candidate/{id}")
    public ResponseEntity<?> findAllByCandidateId(@PathVariable("id") long candidateId,
                                                  @RequestParam(defaultValue = PageDefault.NO) int no,
                                                  @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.jobCareService.findAllByCandidateId(candidateId, no, limit));
    }

    /**
     * Retrieves all the job saves of a candidate.
     *
     * @return         	A ResponseEntity containing the result of the operation.
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/job-save")
    public ResponseEntity<?> findAllJobSave(){
        return ResponseEntity.ok(this.jobCareService.findJobSaveOfCandidateID());
    }


    /**
     * Retrieves all CANDIDATE JOB CARE.
     *
     * @return         	ResponseEntity containing the list of CANDIDATE JOB CARE
     */
    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.jobCareService.findAll());
    }

    /**
     * Deletes a record from the database by the given ID.
     *
     * @param  idJobCare  the ID of the record to delete
     * @return            a ResponseEntity object containing the result of the delete operation
     */
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


