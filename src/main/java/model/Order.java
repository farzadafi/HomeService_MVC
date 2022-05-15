package model;

import lombok.*;
import model.base.Base;
import model.enumoration.OrderStatus;
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

    private Date WorkDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus ;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    private String alley;
    private Integer houseNumber;

    public Order(Customer customer, SubServices subService, Long proposedPrice, String description,
                 Date workDate, String city, String street, String alley, Integer houseNumber) {
        this.customer = customer;
        this.subService = subService;
        this.proposedPrice = proposedPrice;
        this.description = description;
        WorkDate = workDate;
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
                ", WorkDate=" + WorkDate +
                ", orderStatus=" + orderStatus +
                ", city='" + city + '\'' +
                '}';
    }
}
