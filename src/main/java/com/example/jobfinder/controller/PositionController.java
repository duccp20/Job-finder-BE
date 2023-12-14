package com.example.jobfinder.controller;


import com.example.jobfinder.constant.ApiURL;
import com.example.jobfinder.data.dto.request.position.PositionDTO;
import com.example.jobfinder.data.dto.response.ResponseMessage;
import com.example.jobfinder.service.PositionService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiURL.POSITION)
@CrossOrigin(origins = "*", maxAge = 3600)
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(
                ResponseMessage.builder()
                        .httpCode(HttpServletResponse.SC_OK)
                        .message("Get all majors successfully")
                        .data(this.positionService.findAll())
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody PositionDTO positionDTO) {
        return ResponseEntity.ok(this.positionService.create(positionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        return ResponseEntity.ok(this.positionService.deleteById(id));
    }

//    @GetMapping("/statistics")
//    public ResponseEntity<?> statisticsForHR() {
//        return ResponseEntity.ok(this.positionService.statisticsPositionTheNumberOfPostsAndJoins());
//    }
}
