package com.example.HomeService_MVC.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynamicSearchDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String service;
    private Integer stars;
}
