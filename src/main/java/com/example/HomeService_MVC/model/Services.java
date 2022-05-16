package com.example.HomeService_MVC.model;

import com.example.HomeService_MVC.model.base.Base;
import lombok.*;

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
