package dailyfarm.business.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data @Embeddable
public class Location {

    long latitude;
    long longitude;
}
