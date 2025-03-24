package dailyfarm.account;

import dailyfarm.account.dto.*;
import dailyfarm.account.entity.Account;
import dailyfarm.account.entity.RefreshToken;
import dailyfarm.jwt.JwtTools;
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
    private final Class<? extends ProfileResponse> projectionClass;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void register(RegisterRequest registerRequest) {
        if (accountRepository.existsByUsername(registerRequest.username())) {
            throw new EntityExistsException(registerRequest.username());
        }

        T account = accountFactory.create();
        account.setUsername(registerRequest.username());
        account.setPassword(passwordEncoder.encode(registerRequest.password()));

        accountRepository.save(account);
    }

    @Transactional
    public TokenResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        System.out.println(authentication.getPrincipal().getClass());

        String accessToken = JwtTools.generateToken(authentication);

        Account account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccount(account);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);

        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenRepository.findByTokenAndExpiryDateAfter(refreshTokenRequest.refreshToken(), Instant.now())
            .orElseThrow(() -> new RuntimeException("Refresh token is expired or not found"));

        Account account = refreshToken.getAccount();

        refreshTokenRepository.delete(refreshToken);

        String accessToken = JwtTools.generateToken(account.getUsername(), account.getAuthorities());

        refreshToken = new RefreshToken();
        refreshToken.setAccount(account);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plus(30, ChronoUnit.DAYS));

        refreshTokenRepository.save(refreshToken);

        return new TokenResponse(accessToken, refreshToken.getToken());
    }

    @Transactional
    public void logout(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenRepository.deleteByToken(refreshTokenRequest.refreshToken());
    }

    @Transactional(readOnly = true)
    public ProfileResponse me(Authentication authentication) {
        return accountRepository.findByUsername(authentication.getName(), projectionClass)
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));
    }

    @Transactional
    public void changeUsername(ChangeUsernameRequest changeUsernameRequest, Authentication authentication) {
        Account account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        account.setUsername(changeUsernameRequest.username());
    }

    @Transactional
    public void changePassword(ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        Account account = accountRepository.findByUsername(authentication.getName())
            .orElseThrow(() -> new EntityNotFoundException(authentication.getName()));

        account.setPassword(changePasswordRequest.password());
    }
}
