package com.example.HomeService_MVC.dto.services;

import com.example.HomeService_MVC.validation.name.Name;
import com.example.HomeService_MVC.validation.servicesName.ServicesName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServicesSave {

    @Name
    @ServicesName
    private String services;
}
