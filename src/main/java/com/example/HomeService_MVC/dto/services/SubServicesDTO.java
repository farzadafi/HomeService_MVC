package com.example.HomeService_MVC.dto.services;

import com.example.HomeService_MVC.validation.description.Description;
import com.example.HomeService_MVC.validation.name.Name;
import com.example.HomeService_MVC.validation.subServicesName.SubServices;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Min;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubServicesDTO {
    @Column(nullable = false)
    private Integer id;

    @Name
    @SubServices
    private String subServicesName;

    @Min(value=1, message="must be equal or greater than 1")
    @Column(nullable = false)
    private Long minimalPrice;

    @Description
    @Column(nullable = false)
    private String description;
}
