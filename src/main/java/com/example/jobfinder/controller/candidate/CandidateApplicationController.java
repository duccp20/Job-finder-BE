package com.example.jobfinder.controller.candidate;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.constant.PageDefault;
import com.example.jobfinder.data.dto.request.candidate.CandidateApplicationDTO;
import com.example.jobfinder.service.CandidateApplicationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiURL.CANDIDATE_APPLICATION)
public class CandidateApplicationController {
	@Autowired
	private CandidateApplicationService candidateApplicationService;

	@Autowired
	MessageSource messageSource;

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
	@PreAuthorize("hasAuthority('Role_Candidate')")
	@PostMapping(value = "", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> create(@RequestPart("candidateApplication") String candidateApplication,
			@RequestPart(name = "fileCV", required = false) MultipartFile fileCV) {

		CandidateApplicationDTO candidateApplicationDTO = candidateApplicationService.readJson(candidateApplication,
				fileCV);

		// create Date
		// applyListDTO.setCreatedDate(LocalDateTime.now());
		// MailResponse mailResponse = new MailResponse();

		// mailResponse.setTo(job.getHrDTO().getUserCreationDTO().getEmail());
		// mailResponse.setTypeMail(EMailType.ApplyJob);
		// mailResponse.setCv(applyListDTO.getCV());
		// mailResponse.setApply();
		// tam thoi khong gui mail
		// mailService.send(mailResponse);
		return new ResponseEntity<>(this.candidateApplicationService.create(candidateApplicationDTO),
				HttpStatus.CREATED);
	}
	@SecurityRequirement(name = "Bearer Authentication")
	@GetMapping("/check")
	public boolean checkCandidateApplication (@RequestParam("idJob") int idJob) {
		return this.candidateApplicationService.checkCandidateApplication(idJob);
	}

}
