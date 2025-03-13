package dailyfarm.account;

import dailyfarm.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface AccountRepository<T extends Account> extends JpaRepository<T, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<T> findByUsername(String username);

    Optional<T> findByEmail(String email);

    Optional<T> findByPhone(String phone);

    boolean existsByUsernameOrEmailOrPhone(String username, String email, String phone);

    Optional<T> findByUsernameOrEmailOrPhone(String username, String email, String phone);

    default boolean existsByUsernameOrEmailOrPhone(String username) {
        return existsByUsernameOrEmailOrPhone(username, username, username);
    }

    default Optional<T> findByUsernameOrEmailOrPhone(String username) {
        return findByUsernameOrEmailOrPhone(username, username, username);
    }
}
