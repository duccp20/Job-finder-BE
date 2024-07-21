// package com.example.jobfinder.controller.hr;
//
//
// import com.example.jobfinder.constant.ApiURL;
// import io.swagger.v3.oas.annotations.security.SecurityRequirement;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.time.LocalDate;
// import java.time.Month;
//
// @CrossOrigin(origins = "*", maxAge = 3600)
// @RestController
// @RequestMapping(ApiURL.HR_APPLICATION)
// @SecurityRequirement(name = "Bearer Authentication")
// @PreAuthorize("hasAuthority('Role_HR')")
// public class HRApplicationController {
//    @Autowired
//    private HRApplicationService hrApplicationService;
//    @Autowired
//    private JobService jobService;
//
//    @GetMapping("/Recruitment-News")
//    public ResponseEntity<?> getRecruitmentNews(){
//        LocalDate currentDate = LocalDate.now();
//        Month currentMonth = currentDate.getMonth();
//        int monthValue = currentMonth.getValue();
//        return ResponseEntity.ok(this.jobService.recruitmentNews(monthValue));
//    }
//
//
//
////    @GetMapping("")
////    public ResponseEntity<?> findAll(@RequestParam(defaultValue = PageDefault.NO) int no,
////                                     @RequestParam(defaultValue = PageDefault.LIMIT) int limit) {
////        return ResponseEntity.ok(this.hrApplicationService.findAll(no, limit));
////    }
////
////    @PostMapping("")
////    public ResponseEntity<?> create(@RequestBody HRApplicationDTO hrApplicationDTO) {
////        return new ResponseEntity<>(this.hrApplicationService.create(hrApplicationDTO),
////                HttpStatus.CREATED);
////    }
////
////    @PutMapping("/{id}")
////    public ResponseEntity<?> update(@PathVariable int id, @RequestBody HRApplicationDTO hrApplicationDTO) {
////        return ResponseEntity.ok(this.hrApplicationService.update(id, hrApplicationDTO));
////    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<?> findById(@PathVariable int id) {
////        return ResponseEntity.ok(this.hrApplicationService.findById(id));
////    }
////
////    @DeleteMapping("/{id}")
////    public ResponseEntity<?> deleteById(@PathVariable int id) {
////        this.hrApplicationService.deleteById(id);
////        return ResponseEntity.ok("DELETED");
////    }
// }
