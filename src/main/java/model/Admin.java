package model;

import lombok.*;
import model.base.User;
import model.enumoration.Role;

import javax.persistence.Entity;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Admin extends User {

    public Admin(String firstName, String lastName, String email, String password, Date date, Long balance, Role userType) {
        super(firstName, lastName, email, password, date, balance, userType);
    }
}
