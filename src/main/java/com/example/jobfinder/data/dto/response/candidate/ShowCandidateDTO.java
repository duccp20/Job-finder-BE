package com.example.jobfinder.data.dto.response.candidate;


import com.example.jobfinder.data.dto.response.user.ShowUserDTO;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowCandidateDTO {

   Map <String, Object> data;

}
