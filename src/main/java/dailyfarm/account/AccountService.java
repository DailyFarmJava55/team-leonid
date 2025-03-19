package dailyfarm.account;

import dailyfarm.account.dto.*;
import dailyfarm.account.entity.Account;
import dailyfarm.account.entity.RefreshToken;
import dailyfarm.jwt.JwtService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Slf4j @RequiredArgsConstructor
public class AccountService<T extends Account> {

    private final AuthenticationManager authenticationManager;
    private final AccountRepository<T> accountRepository;
    private final AccountFactory<T> accountFactory;
    private final PasswordEncoder passwordEncoder;
    private final Class<? extends AccountResponse> projectionClass;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void register(RegisterRequest request) {
        if (accountRepository.existsByUsername(request.username())) {
            throw new EntityExistsException(request.username());
        }

        T account = accountFactory.create();

        account.setUsername(request.username());
        account.setPassword(passwordEncoder.encode(request.password()));

        accountRepository.save(account);
    }

    @Transactional
    public TokenResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        String accessToken = JwtService.generateToken(authentication);

        Account account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(EntityNotFoundException::new);

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAccount(account);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);

        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public TokenResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenAndExpiryDateAfter(request.refreshToken(), Instant.now())
            .orElseThrow(() -> new RuntimeException("Refresh token is expired or not found"));

        Account account = refreshToken.getAccount();

        refreshTokenRepository.delete(refreshToken);

        String accessToken = JwtService.generateToken(account.getUsername(), account.getRoles());

        refreshToken = new RefreshToken();

        refreshToken.setAccount(account);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);

        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public void logout(RefreshTokenRequest request) {
        refreshTokenRepository.deleteByToken(request.refreshToken());
    }

    @Transactional
    public AccountResponse getProfile(Authentication authentication) {
        return accountRepository.findByUsername(authentication.getName(), projectionClass)
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));
    }

    @Transactional
    public void changeUsername(ChangeUsernameRequest request, Authentication authentication) {
        T account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        account.setUsername(request.username());
    }

    @Transactional
    public void changePassword(ChangePasswordRequest request, Authentication authentication) {
        T account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        account.setPassword(request.password());
    }

    // TODO: Domain Logic

    // TODO: Refresh Token HttpOnly Cookies

    // TODO: Update Profile

    // TODO: Reset Password

    // TODO: Email

    // TODO: Change Email

    // TODO: Swagger
    // TODO: Postman

    // TODO: @Transactional
    // TODO: @Entity @Version

    // TODO: Authentication
    // TODO: Configuration
    // TODO: Dependencites
    // TODO: Properties
    // TODO: Security
    // TODO: Logging
    // TODO: Validation
    // TODO: AuditorAware
    // TODO: Exception Handling
    // TODO: Clean Architecture

    // TODO: Email Verification & OTP

    // TODO: Username vs UUID

    // TODO: Roles
    // TODO: Authorities

    // TODO: UserDetails
    // TODO: GenericFilterBean
    // TODO: AbstractAuthenticationToken

    // https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/oauth2/core/package-summary.html
    // https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/oauth2/client/package-summary.html
    // https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/oauth2/server/resource/package-summary.html
    // https://docs.spring.io/spring-security/reference/api/java/org/springframework/security/oauth2/jwt/package-summary.html

    // TODO: InMemory JWT Invalidation
    // TODO: Refresh Token Invalidation

    // TODO: Remove Expired Tokens from the Database
    // TODO: Remove Unused Tokens from the Database

    // TODO: RSAPublicKey
    // TODO: RSAPrivateKey
}
