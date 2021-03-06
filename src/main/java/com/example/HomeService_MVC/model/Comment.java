package com.example.HomeService_MVC.model;

import com.example.HomeService_MVC.model.base.Base;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class Comment extends Base<Integer> {
    @Column(length = 4096)
    private String comment;
    private Integer stars;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Expert expert;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Customer customer;
}
