package dailyfarm.account.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Data @Entity
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RefreshToken {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID uuid;

    @ManyToOne(optional = false)
    private Account account;

    @Column(nullable = false, unique = true, updatable = false)
    private String token;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdDate;

    @Column(nullable = false, updatable = false)
    private Instant expiryDate;
}
