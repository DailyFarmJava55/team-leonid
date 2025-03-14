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
        log.info(String.valueOf(authentication));

        return accountRepository.findByUsername(authentication.getName(), projectionClass).
            orElseThrow(() -> new EntityNotFoundException(authentication.getName()));
    }

    // TODO: Update Profile
    // TODO: Change Username
    // TODO: Change Password
    // TODO: Reset Password
    // TODO: Hibernate Validation
    // TODO: Application Properties
    // TODO: Refresh Token Rotation
    // TODO: Revoke Refresh Tokens
    // TODO: Swagger Configuration
    // TODO: Rest Controller Advice
    // TODO: Business Logic
    // TODO: Class Based Authentication
}
