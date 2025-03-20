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

        System.out.println(authentication.getPrincipal().getClass());

        String accessToken = JwtTools.generateToken(authentication);

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

        String accessToken = JwtTools.generateToken(account.getUsername(), account.getAuthorities());

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

    // TODO: Surprise-Bag

    // TODO: Orders

    // TODO: Change Location

    // TODO: Email Verification & OTP

    // TODO: Email

    // TODO: Change Email

    // TODO: Reset Password

    // TODO: Refresh Token HttpOnly

    // TODO: Update Profile

    // TODO: Username vs UUID

    // TODO: Authentication
    // TODO: Configuration
    // TODO: Dependencites
    // TODO: Properties
    // TODO: Security
    // TODO: Logging
    // TODO: Validation
    // TODO: Exception Handling
    // TODO: Clean Architecture

    // TODO: AuditorAware

    // TODO: @Transactional
    // TODO: @Entity @Version

    // TODO: Swagger
    // TODO: Postman

    // TODO: Roles
    // TODO: Authorities

    // TODO: (User: UserDetails) !== Principal
    // TODO: Authentication(principal: Object)
    // TODO: Authentication(Object): Principal

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

    // TODO: @CreatedBy, @LastModifiedBy

    // TODO: Unidirectional Data Flow

    // TODO: Single Responsibility Principle

    // class SpringSecurityAuditorAware implements AuditorAware<User> {
    //
    //   @Override
    //   public Optional<User> getCurrentAuditor() {
    //
    //     return Optional.ofNullable(SecurityContextHolder.getContext())
    //             .map(SecurityContext::getAuthentication)
    //             .filter(Authentication::isAuthenticated)
    //             .map(Authentication::getPrincipal)
    //             .map(User.class::cast);
    //   }
    // }

    // class SpringSecurityAuditorAware implements AuditorAware<User> {
    //
    //   public User getCurrentAuditor() {
    //
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //
    //     if (authentication == null || !authentication.isAuthenticated()) {
    //       return null;
    //     }
    //
    //     return ((MyUserDetails) authentication.getPrincipal()).getUser();
    //   }
    // }

    // Method Arguments
    // java.security.Principal
    //
    // Currently authenticated user—possibly a specific Principal implementation
    // class if known.
    //
    // Note that this argument is not resolved eagerly, if it is annotated in order to allow
    // a custom resolver to resolve it before falling back on default resolution via
    // HttpServletRequest#getUserPrincipal. For example, the Spring Security
    // Authentication implements Principal and would be injected as such via
    // HttpServletRequest#getUserPrincipal, unless it is also annotated with
    // @AuthenticationPrincipal in which case it is resolved by a custom Spring
    // Security resolver through Authentication#getPrincipal.

    // public interface Principal
    //    boolean equals(Object another);
    //    String toString();
    //    int hashCode();
    //    String getName();

    // public interface Authentication extends Principal
    //    Collection<? extends GrantedAuthority> getAuthorities();
    //    Object getCredentials();
    //    Object getDetails();
    //    Object getPrincipal();
    //    boolean isAuthenticated();
    //    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;

    // public interface CredentialsContainer
    //    void eraseCredentials();

    // public abstract class AbstractAuthenticationToken implements Authentication, CredentialsContainer
    //    public String getName()
    //        if (this.getPrincipal() instanceof UserDetails userDetails)
    //            return userDetails.getUsername();
    //        if (this.getPrincipal() instanceof AuthenticatedPrincipal authenticatedPrincipal)
    //            return authenticatedPrincipal.getName();
    //        if (this.getPrincipal() instanceof Principal principal)
    //            return principal.getName();
    //        return (this.getPrincipal() == null) ? "" : this.getPrincipal().toString();
    //    public void eraseCredentials() {
    //        eraseSecret(getCredentials());
    //        eraseSecret(getPrincipal());
    //        eraseSecret(this.details);
    //    private void eraseSecret(Object secret)
    //        if (secret instanceof CredentialsContainer container)
    //            container.eraseCredentials();

    // public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken
    //    public UsernamePasswordAuthenticationToken(Object principal, Object credentials)
    //        super(null);
    //        this.principal = principal;
    //        this.credentials = credentials;
    //        setAuthenticated(false);
    //    public UsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    //        super(authorities);
    //        this.principal = principal;
    //        this.credentials = credentials;
    //        super.setAuthenticated(true); // must use super, as we override
    //    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    //        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
    //        super.setAuthenticated(false);
    //    public void eraseCredentials()
    //        super.eraseCredentials();
    //        this.credentials = null;
}
