package dailyfarm.orders.entity;

import dailyfarm.customer.entity.Customer;
import dailyfarm.surprisebag.entity.SurpriseBag;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Orders {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @ManyToOne(optional = false)
    private SurpriseBag surpriseBag;

    // TODO: Order Status?
    // TODO: Order Item?
    // TODO: Quantity?
    // TODO: Price At Order Time?
}
