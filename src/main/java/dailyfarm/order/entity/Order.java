package dailyfarm.order.entity;

import dailyfarm.customer.entity.Customer;
import dailyfarm.surprisebag.entity.SurpriseBag;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Order {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID uuid;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private SurpriseBag surpriseBag;
}
