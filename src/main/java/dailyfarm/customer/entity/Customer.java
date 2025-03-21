package dailyfarm.customer.entity;

import dailyfarm.account.entity.Account;
import dailyfarm.order.entity.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @Entity
@EqualsAndHashCode(callSuper = true)
public class Customer extends Account {

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<Order> orders;

    public Customer() {
        getAuthorities().add("ROLE_CUSTOMER");
    }
}
