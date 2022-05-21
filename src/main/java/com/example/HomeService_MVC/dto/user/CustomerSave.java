package com.example.HomeService_MVC.dto.user;

import lombok.*;
import com.example.HomeService_MVC.validation.image.email.Email;
import com.example.HomeService_MVC.validation.name.Name;
import com.example.HomeService_MVC.validation.password.Password;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSave {
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

    @Password
    public String[] getPassword(){
        return new String[]{password,confPassword};
    }
}
