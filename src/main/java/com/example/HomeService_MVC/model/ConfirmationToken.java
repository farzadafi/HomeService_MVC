package com.example.HomeService_MVC.model;

import com.example.HomeService_MVC.model.base.Base;
import com.example.HomeService_MVC.model.base.User;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfirmationToken extends Base<Integer> {

    private String confirmToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @OneToOne
    private User user;

    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        confirmToken = UUID.randomUUID().toString();
    }


}
