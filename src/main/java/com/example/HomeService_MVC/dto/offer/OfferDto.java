package com.example.HomeService_MVC.dto.offer;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDto {

    private Integer id;

    @NotNull(message = "شما باید یک قیمت پیشنهادی وارد کنید")
    @Min(value=1, message="قیمت پیشنهادی نباید منفی باشد")
    private Long proposedPrice;

    @NotNull(message = "شما باید یک زمان صحیح وارد کنید")
    @Min(value=1, message="زمان تقریبی انجام کار برابر یا بزرگ از ۱ باید باشد")
    private Integer durationWork;

    @NotNull(message = "شما باید یک زمان صحیح وارد کنید")
    private LocalTime startTime;

    private Integer orderId;

}
