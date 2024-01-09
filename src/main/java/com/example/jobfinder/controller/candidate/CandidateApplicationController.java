package com.example.jobfinder.controller.candidate;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.constant.PageDefault;
import com.example.jobfinder.data.dto.request.candidate.CandidateApplicationDTO;
import com.example.jobfinder.data.dto.request.candidate.CandidateProfileDTO;
import com.example.jobfinder.service.CandidateApplicationService;
import com.example.jobfinder.service.JsonReaderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(ApiURL.CANDIDATE_APPLICATION)
public class CandidateApplicationController {
    @Autowired
    private CandidateApplicationService candidateApplicationService;

    @Autowired
    private JsonReaderService<Object> jsonReaderService;
    @Autowired
    MessageSource messageSource;

    /**
     * Creates a new candidate application.
     *
     * @param fileCV               the CV file (optional)
     * @return the response entity containing the result of the creation process
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> create(
            @RequestPart("candidateApplication") String candidateApplicationDTOJson,
            @RequestPart(name = "fileCV", required = false) MultipartFile fileCV) throws IOException {

        CandidateApplicationDTO candidateApplicationDTO = jsonReaderService.readValue(candidateApplicationDTOJson, CandidateApplicationDTO.class);


        // create Date
        // applyListDTO.setCreatedDate(LocalDateTime.now());
        // MailResponse mailResponse = new MailResponse();

        // mailResponse.setTo(job.getHrDTO().getUserCreationDTO().getEmail());
        // mailResponse.setTypeMail(EMailType.ApplyJob);
        // mailResponse.setCv(applyListDTO.getCV());
        // mailResponse.setApply();
        // tam thoi khong gui mail
        // mailService.send(mailResponse);
        return new ResponseEntity<>(this.candidateApplicationService.create(candidateApplicationDTO, fileCV),
                HttpStatus.CREATED);
    }

    /**
     * Retrieves all candidate applications by candidate ID.
     *
     * @param no    the page number to retrieve (default: 0)
     * @param limit the maximum number of results to return (default: 10)
     * @return the response entity containing the candidate applications
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_Candidate')")
    @GetMapping("/candidate")
    public ResponseEntity<?> findAllByCandidateId(
            @RequestParam(defaultValue = PageDefault.NO) int no,
            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.candidateApplicationService.findAllByCandidateId(no, limit));
    }


    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/job/{id}")
    public ResponseEntity<?> findAllByJobId(@PathVariable int id, @RequestParam(defaultValue = PageDefault.NO) int no,
                                            @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
        return ResponseEntity.ok(this.candidateApplicationService.findAllByJobId(id, no, limit));
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAuthority('Role_HR')")
    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return ResponseEntity.ok(this.candidateApplicationService.deleteById(id));
    }




    /**
     * Checks if a candidate application exists for a given job ID.
     *
     * @param idJob the ID of the job to check the candidate application for
     * @return true if a candidate application exists, false otherwise
     */
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/check")
    public ResponseEntity<?> checkCandidateApplication(@RequestParam("idJob") int idJob) {
        try {
            return ResponseEntity.ok(this.candidateApplicationService.checkCandidateApplication(idJob));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
