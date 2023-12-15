package com.example.jobfinder.data.dto.request.hr;

import com.example.jobfinder.data.dto.request.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class HRDTO {
    private Long id;
    private UserDTO userDTO;
    private HROtherInfoDTO hrOtherInfoDTO;
}
