package dailyfarm.customer.entity;

import dailyfarm.account.entity.AccountEntity;
import dailyfarm.order.entity.OrderEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @Entity
@EqualsAndHashCode(callSuper = true)
public class CustomerEntity extends AccountEntity {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<OrderEntity> orders;

    public CustomerEntity() {
        getAuthorities().add("ROLE_CUSTOMER");
    }
}
