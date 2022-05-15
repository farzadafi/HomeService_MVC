package model;

import lombok.*;
import model.base.Base;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class SubService extends Base<Integer> {

    @ManyToMany( mappedBy = "subServices",fetch = FetchType.LAZY)
    private Set<Expert> experts;

    private Long minimalPrice;
    private String description;
}
