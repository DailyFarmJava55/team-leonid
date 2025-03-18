package dailyfarm.account;

import dailyfarm.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface AccountRepository<T extends Account> extends JpaRepository<T, Long> {

    Optional<T> findByUsername(String username);

    <R> Optional<R> findByUsername(String username, Class<R> type);

    Optional<T> findByUuid(UUID uuid);

    <R> Optional<R> findByUuid(UUID uuid, Class<R> type);
}
