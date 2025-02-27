package telran.java55.teamleonid.business.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class GeoLocation {

    @Column(nullable = false)
    Double latitude;
    @Column(nullable = false)
    Double longitude;
}
