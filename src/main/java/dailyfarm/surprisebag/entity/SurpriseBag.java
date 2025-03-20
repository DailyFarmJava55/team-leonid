package dailyfarm.surprisebag.entity;

import dailyfarm.business.entity.Business;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SurpriseBag {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    private Business business;

    // TODO: TAGS Or/And TYPE(S)?
    // TODO: Price?
    // TODO: Product Stock?
    // TODO: Product Id?
    // TODO: Maps Id?
    // TODO: Product?
    // TODO: Available Stock?
    // TODO: Reserved Stock?
}
