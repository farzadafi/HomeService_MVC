package com.example.HomeService_MVC.dto;


import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentDto {
    @Min(value = 0,message = "price have more than 0")
    private Integer price;

    @Pattern(regexp = "[0-9]{16}",message = "number have 16 numeric character")
    private String creditCardNumber;

    @Min(value = 1,message = "month have a number between 1 and 12" )
    @Max(value = 12,message = "month have a number between 1 and 12" )
    private Integer monthExpire;

    @Min(value = 0,message = "year have between 0 and 99" )
    @Max(value = 99,message = "year have between 0 and 99" )
    private Integer yearExpire;

    @Min(value = 111,message = " cvv2 have between 111 and 9999" )
    @Max(value = 9999,message = " cvv2 have between 111 and 9999" )
    private Integer cvv2;

    @Min(value = 1111,message = "second password have grater than 1111")
    private Integer secondPassword;
}
