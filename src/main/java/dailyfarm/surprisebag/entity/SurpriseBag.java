package dailyfarm.surprisebag.entity;

import dailyfarm.business.entity.Business;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SurpriseBag {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID uuid;

    @ManyToOne(optional = false)
    private Business business;
}
