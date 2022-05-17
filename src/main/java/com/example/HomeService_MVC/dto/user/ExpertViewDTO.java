package com.example.HomeService_MVC.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpertViewDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
}
