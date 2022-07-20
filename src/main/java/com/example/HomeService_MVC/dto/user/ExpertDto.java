package com.example.HomeService_MVC.dto.user;

import com.example.HomeService_MVC.validation.email.Email;
import com.example.HomeService_MVC.validation.image.Image;
import com.example.HomeService_MVC.validation.name.Name;
import com.example.HomeService_MVC.validation.password.Password;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertDto {
    private Integer id;

    @Column(nullable = false)
    @Name
    private String firstName;

    @Column(nullable = false)
    @Name
    private String lastName;

    @Column(unique = true,nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String confPassword;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    @Image
    private MultipartFile image;

    @Password
    public String[] getPasswordAndConfPassword(){
        return new String[]{password,confPassword};
    }
}
