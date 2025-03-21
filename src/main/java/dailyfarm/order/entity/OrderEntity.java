package dailyfarm.order.entity;

import dailyfarm.customer.entity.CustomerEntity;
import dailyfarm.surprisebag.entity.SurpriseBagEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderEntity {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID uuid;

    @ManyToOne(optional = false)
    private CustomerEntity customer;

    @ManyToOne(optional = false)
    private SurpriseBagEntity surpriseBag;

    // TODO: Order Status?
    // TODO: Order Item?
    // TODO: Quantity?
    // TODO: Price At Order Time?
}
