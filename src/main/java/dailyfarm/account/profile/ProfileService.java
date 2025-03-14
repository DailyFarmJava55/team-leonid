package dailyfarm.account.profile;

import dailyfarm.account.AccountRepository;
import dailyfarm.account.entity.Account;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

@Slf4j @RequiredArgsConstructor
public class ProfileService<T extends Account> {

    private final AccountRepository<T> accountRepository;
    private final Class<? extends ProfileResponse> projectionClass;

    public ProfileResponse getProfile(Authentication authentication) {
        log.info(authentication.toString());

        return accountRepository.findByUsername(authentication.getName(), projectionClass).
            orElseThrow(() -> new EntityNotFoundException(authentication.getName()));
    }
}
