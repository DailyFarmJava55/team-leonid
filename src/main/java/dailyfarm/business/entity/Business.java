package dailyfarm.business.entity;

import dailyfarm.account.entity.Account;
import dailyfarm.surprisebag.entity.SurpriseBag;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @Entity
@EqualsAndHashCode(callSuper = true)
public class Business extends Account {

    @Embedded
    LocationValue location;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    List<SurpriseBag> surpriseBags;

    public Business() {
        getAuthorities().add("ROLE_BUSINESS");
    }
}
