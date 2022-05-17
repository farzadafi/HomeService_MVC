package com.example.HomeService_MVC.dto.order;

import com.example.HomeService_MVC.validation.description.Description;
import com.example.HomeService_MVC.validation.name.Name;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @Column(nullable = false)
    @Min(value=1, message="must be equal or greater than 1")
    private Long proposedPrice;

    @Column(nullable = false)
    @Description
    private String description;

    @Column(nullable = false)
    private Date WorkDate;

    @Name
    @Column(nullable = false)
    private String city;

    private String street;
    private String alley;
    private Integer houseNumber;
}
