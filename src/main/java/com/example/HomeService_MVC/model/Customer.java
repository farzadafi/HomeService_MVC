package com.example.HomeService_MVC.model;

import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.model.enumoration.Role;
import lombok.*;

import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@Builder
@Entity
public class Customer extends User {

    public Customer(String firstName, String lastName, String email, String password, Date date, Long balance, Role userType) {
        super(firstName, lastName, email, password, date, balance, userType);
    }

    public Customer() {
    }

    @Override
    public String toString() {
        return "User{" +
                ", userType=" + getRole() +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", date=" + getDate() +
                ", balance=" + getBalance() +
                '}';
    }
}
