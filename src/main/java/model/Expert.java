package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.base.User;
import model.enumoration.Role;

import javax.persistence.*;
import java.util.Date;
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

    @JoinTable(name = "expert_subservices",
            joinColumns = {@JoinColumn(name = "expert_id")},
            inverseJoinColumns = {@JoinColumn(name = "subservices_id")})
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<SubService> subServices;

    public Expert(String firstName, String lastName, String email, String password, Date date, Long balance, Role userType, String city, byte[] image, Integer stars, boolean accepted, Set<SubService> subServices) {
        super(firstName, lastName, email, password, date, balance, userType);
        this.city = city;
        this.image = image;
        this.stars = stars;
        this.accepted = accepted;
        this.subServices = subServices;
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
