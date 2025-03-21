package dailyfarm.surprisebag.entity;

import dailyfarm.business.entity.BusinessEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SurpriseBagEntity {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID uuid;

    @ManyToOne(optional = false)
    private BusinessEntity business;

    // TODO: TAGS Or/And TYPE(S)?
    // TODO: Price?
    // TODO: Product Stock?
    // TODO: Product Id?
    // TODO: Maps Id?
    // TODO: Product?
    // TODO: Available Stock?
    // TODO: Reserved Stock?
}
