package dailyfarm.business.entity;

import dailyfarm.account.entity.AccountEntity;
import dailyfarm.surprisebag.entity.SurpriseBagEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data @Entity
@EqualsAndHashCode(callSuper = true)
public class BusinessEntity extends AccountEntity {

    @Embedded
    LocationValue location;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    List<SurpriseBagEntity> surpriseBags;

    public BusinessEntity() {
        getAuthorities().add("ROLE_BUSINESS");
    }
}
