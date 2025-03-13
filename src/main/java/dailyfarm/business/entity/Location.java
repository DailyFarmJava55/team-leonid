package dailyfarm.business.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    long latitude;
    long longitude;
}
