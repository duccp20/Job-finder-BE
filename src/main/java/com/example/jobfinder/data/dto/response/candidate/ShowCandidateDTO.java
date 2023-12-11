package com.example.jobfinder.data.dto.response.candidate;


import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowCandidateDTO {

    private ShowUserDTO showUserDTO;

    private String university;

}
