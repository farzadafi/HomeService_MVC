package com.example.HomeService_MVC.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.HomeService_MVC.model.base.User;
import com.example.HomeService_MVC.model.enumoration.Role;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Expert extends User {
    private String city;
    private byte[] image;
    private Integer stars ;
    private boolean accepted ;

    @JoinTable(name = "expert_subServices",
            joinColumns = {@JoinColumn(name = "expert_id")},
            inverseJoinColumns = {@JoinColumn(name = "subServices_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SubServices> subServices;

    public Expert(String firstName, String lastName, String email, String password, Long balance, Role role, String city, byte[] image, Integer stars) {
        super(firstName, lastName, email, password, balance, role);
        this.city = city;
        this.image = image;
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Expert{" +
                "id= " + super.getId() +
                "firstName= " + super.getFirstName() +
                "lastName= " + super.getLastName() +
                "city= '" + city + '\'' +
                ", stars= " + stars +
                ", accepted= " + accepted +
                '}';
    }
}
