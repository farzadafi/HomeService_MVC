package com.example.HomeService_MVC.model;

import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.model.enumoration.Role;
import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Admin extends User {

    public Admin(String firstName, String lastName, String email, String password, Date date, Long balance, Role userType) {
        super(firstName, lastName, email, password, date, balance, userType);
    }
}
