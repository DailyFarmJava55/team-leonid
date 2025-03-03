package dailyfarm.busines.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class GeoLocation {

    @Column(nullable = false)
    Double latitude;
    @Column(nullable = false)
    Double longitude;
}
