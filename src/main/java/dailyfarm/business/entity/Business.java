package dailyfarm.business.entity;

import dailyfarm.account.entity.Account;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data @Entity
@EqualsAndHashCode(callSuper = true)
public class Business extends Account {

    @Embedded
    Location location;

    public Business() {
        getRoles().add("ROLE_BUSINESS");
    }
}
