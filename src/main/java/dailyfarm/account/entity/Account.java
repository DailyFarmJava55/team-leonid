package dailyfarm.account.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data @MappedSuperclass
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    private final Set<String> roles = new HashSet<>();
}
