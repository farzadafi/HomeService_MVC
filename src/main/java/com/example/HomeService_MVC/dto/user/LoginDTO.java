package com.example.HomeService_MVC.dto.user;

import com.example.HomeService_MVC.validation.email.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {
    @Email
    private String email;

    private String password;
}
