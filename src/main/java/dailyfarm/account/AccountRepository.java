package dailyfarm.account;

import dailyfarm.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AccountRepository<T extends Account> extends JpaRepository<T, Long> {

    Optional<T> findByUsername(String username);

    <R> Optional<R> findByUsername(String username, Class<R> type);
}
