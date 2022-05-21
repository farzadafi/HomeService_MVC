package com.example.HomeService_MVC.dto.user;

import com.example.HomeService_MVC.validation.image.email.Email;
import com.example.HomeService_MVC.validation.password.Password;
import lombok.*;
import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDTO {
    @Column(unique = true,nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confPassword;

    @Password
    public String[] getPassword(){
        return new String[]{password,confPassword};
    }
}
