package dailyfarm.account;

import dailyfarm.account.dto.*;
import dailyfarm.account.entity.Account;
import dailyfarm.jwt.JwtAuthenticationProvider;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j @RequiredArgsConstructor
public class AccountService<T extends Account> {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository<T> accountRepository;
    private final AccountFactory<T> accountFactory;
    private final PasswordEncoder passwordEncoder;
    private final Class<? extends AccountResponse> projectionClass;

    public void register(RegisterRequest request) {
        T account = accountFactory.create();

        account.setUsername(request.username());
        account.setPassword(passwordEncoder.encode(request.password()));

        accountRepository.save(account);
    }

    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String accessToken = JwtAuthenticationProvider.generateToken(authentication);
        String refreshToken = "success";

        return new TokenResponse(accessToken, refreshToken);
    }

    public AccountResponse me(Authentication authentication) {
        return accountRepository.findByUsername(authentication.getName(), projectionClass)
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));
    }

    public void changeUsername(ChangeUsernameRequest request, Authentication authentication) {
        T account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        account.setUsername(request.username());
    }

    public void changePassword(ChangePasswordRequest request, Authentication authentication) {
        T account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        account.setPassword(request.password());
    }

    // TODO: Generic Classes

    // TODO: Opaque Refresh Token

    // TODO: Refresh Token Rotation
    // TODO: Revoke Refresh Tokens

    // TODO: Logout

    // TODO: Update Profile

    // TODO: Reset Password

    // TODO: Change Email

    // TODO: Domain Logic

    // TODO: Swagger
    // TODO: Postman

    // TODO: @Transactional
    // TODO: @Entity @Version

    // TODO: Authentication
    // TODO: Configuration
    // TODO: Properties
    // TODO: Security
    // TODO: Logging
    // TODO: Validation
    // TODO: Exception Handling
    // TODO: Clean Architecture

    // TODO: Username vs UUID

    // TODO: Roles
    // TODO: Authorities

    // TODO: UserDetails
    // TODO: GenericFilterBean
    // TODO: AbstractAuthenticationToken

    // TODO: RSAPublicKey
    // TODO: RSAPrivateKey
}
