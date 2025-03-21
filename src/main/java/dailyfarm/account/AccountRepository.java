package dailyfarm.account;

import dailyfarm.account.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AccountRepository<T extends AccountEntity> extends JpaRepository<T, Long> {

    boolean existsByUsername(String username);

    Optional<T> findByUsername(String username);

    <R> Optional<R> findByUsername(String username, Class<R> type);
}
