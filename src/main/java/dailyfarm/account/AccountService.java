package dailyfarm.account;

import dailyfarm.account.dto.*;
import dailyfarm.account.entity.Account;
import dailyfarm.jwt.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Principal;

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

        String accessToken = JwtUtils.generateToken(authentication);
        String refreshToken = "success";

        return new TokenResponse(accessToken, refreshToken);
    }

    public AccountResponse getProfile(Principal principal) {
        return accountRepository
            .findByUsername(principal.getName(), projectionClass)
            .orElseThrow(() -> new EntityNotFoundException(principal.getName()));
    }

    public void changeUsername(Principal principal, ChangeUsernameRequest request) {
        T account = accountRepository
            .findByUsername(principal.getName())
            .orElseThrow(() -> new EntityNotFoundException(principal.getName()));

        account.setUsername(request.username());
    }

    public void changePassword(Principal principal, ChangePasswordRequest request) {
        T account = accountRepository
            .findByUsername(principal.getName())
            .orElseThrow(() -> new EntityNotFoundException(principal.getName()));

        account.setPassword(request.password());
    }

    // TODO: Username vs UUID

    // TODO: Opaque Refresh Token

    // TODO: Refresh Token Rotation
    // TODO: Revoke Refresh Tokens

    // TODO: Logout

    // TODO: Update Profile

    // TODO: Reset Password

    // TODO: Change Email

    // TODO: Domain

    // TODO: Swagger
    // TODO: Postman

    // TODO: @Transactional

    // TODO: Authentication
    // TODO: Configuration
    // TODO: Properties
    // TODO: Security
    // TODO: Logging
    // TODO: Validation
    // TODO: Exception Handling
    // TODO: Clean Architecture

    // TODO: Roles
    // TODO: Authorities

    // TODO: UserDetails
    // TODO: GenericFilterBean
    // TODO: AbstractAuthenticationToken

    // TODO: RSAPublicKey
    // TODO: RSAPrivateKey

    // TODO: @Entity @Version
}
