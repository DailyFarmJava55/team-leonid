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

    public ProfileResponse getProfile(Authentication authentication) {
        log.info(authentication.toString());

        T account = accountRepository.findByUsernameOrEmailOrPhone(authentication.getName()).
            orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        return new ProfileResponse(account.getUsername(), account.getEmail(), account.getPhone());
    }
}
