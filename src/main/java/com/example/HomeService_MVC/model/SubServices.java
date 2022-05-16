package com.example.HomeService_MVC.model;

import com.example.HomeService_MVC.model.base.Base;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class SubServices extends Base<Integer> {

    @ManyToMany( mappedBy = "subServices",fetch = FetchType.LAZY)
    private Set<Expert> experts;
    @ManyToOne
    private Services services;

    private Long minimalPrice;
    private String description;
}
