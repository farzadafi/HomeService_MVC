package model;

import lombok.*;
import model.base.Base;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Services extends Base<Integer> {
    @Column(nullable = false,unique = true)
    private String services;

    public Services(String services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Services{" +
                "id=" + super.getId() +
                ",services='" + services + '\'' +
                '}';
    }
}
