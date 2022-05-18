package com.example.HomeService_MVC.dto.comment;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Integer id;

    private String comment;

    @Min(value = 1,message = "You have to enter a number between 1 or 5")
    @Max(value = 5,message = "You have to enter a number between 1 or 5")
    private Integer stars;
}
