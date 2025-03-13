package dailyfarm.customer.entity;

import dailyfarm.account.entity.Account;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @Entity
@EqualsAndHashCode(callSuper = true)
public class Customer extends Account {

    public Customer() {
        getRoles().add("ROLE_CUSTOMER");
    }
}
