package model;

import lombok.*;
import model.base.User;
import model.enumoration.Role;

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
                ", userType=" + getUserType() +
                "firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", date=" + getDate() +
                ", balance=" + getBalance() +
                '}';
    }
}
