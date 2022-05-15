package model;

import lombok.*;
import model.base.Base;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"expert_id", "orders_id"})
})
public class Offer extends Base<Integer> {

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private Long proposedPrice;
    private Integer durationWork;
    private LocalTime startTime;
    @ManyToOne(fetch = FetchType.LAZY)
    private Order orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Expert expert;

    private boolean isAccepted;

    public Offer(Long proposedPrice, Integer durationWork, LocalTime startTime, Order orders, Expert expert) {
        this.proposedPrice = proposedPrice;
        this.durationWork = durationWork;
        this.startTime = startTime;
        this.orders = orders;
        this.expert = expert;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + super.getId() +
                "date=" + date +
                ", proposedPrice=" + proposedPrice +
                ", durationWork=" + durationWork +
                ", startTime=" + startTime +
                ", expert=" + expert +
                '}';
    }
}
