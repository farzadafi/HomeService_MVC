package com.example.HomeService_MVC.dto.offer;

import lombok.*;

import javax.validation.constraints.Min;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDTO {

    private Integer id;

    @Min(value=1, message="must be equal or greater than 1")
    private Long proposedPrice;

    @Min(value=1, message="must be equal or greater than 1")
    private Integer durationWork;

    private LocalTime startTime;

}
