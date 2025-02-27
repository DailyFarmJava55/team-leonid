package telran.java55.teamleonid.business.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Setter @Getter
public class Business {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Embedded
    private GeoLocation geoLocation;
}
