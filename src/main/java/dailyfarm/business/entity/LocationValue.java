package dailyfarm.business.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data @Embeddable
public class LocationValue {

    double latitude;
    double longitude;
}
