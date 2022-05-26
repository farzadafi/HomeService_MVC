package com.example.HomeService_MVC.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DynamicSearch {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String service;
    private Integer stars;
}
