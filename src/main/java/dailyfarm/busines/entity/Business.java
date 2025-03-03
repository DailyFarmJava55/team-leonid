package dailyfarm.busines.entity;

import dailyfarm.user.entity.User;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Business extends User {

    @Embedded
    private GeoLocation geoLocation;
}
