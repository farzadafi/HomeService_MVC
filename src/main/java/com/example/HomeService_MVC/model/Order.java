package com.example.HomeService_MVC.model;

import lombok.*;
import com.example.HomeService_MVC.model.base.Base;
import com.example.HomeService_MVC.model.enumoration.OrderStatus;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "orders")
public class Order extends Base<Integer> {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    private SubServices subService;

    private Long proposedPrice;
    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date entryDate;

    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus ;

    @Column(nullable = false)
    private String city;
    private String street;
    private String alley;
    private Integer houseNumber;

    public Order(Customer customer, SubServices subService, Long proposedPrice, String description,
                 Date date, String city, String street, String alley, Integer houseNumber) {
        this.customer = customer;
        this.subService = subService;
        this.proposedPrice = proposedPrice;
        this.description = description;
        this.date = date;
        this.city = city;
        this.street = street;
        this.alley = alley;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + super.getId() +
                "subServices=" + subService +
                ", proposedPrice=" + proposedPrice +
                ", description='" + description + '\'' +
                ", entryDate=" + entryDate +
                ", WorkDate=" + date +
                ", orderStatus=" + orderStatus +
                ", city='" + city + '\'' +
                '}';
    }
}
