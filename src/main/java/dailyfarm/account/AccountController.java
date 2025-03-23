package dailyfarm.account;

import dailyfarm.account.dto.*;
import dailyfarm.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
public class AccountController<T extends Account> {

    private final AccountService<T> accountService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest registerRequest) {
        accountService.register(registerRequest);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        return accountService.login(loginRequest);
    }

    @PostMapping("refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return accountService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        accountService.logout(refreshTokenRequest);
    }

    @GetMapping("me")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse me(Authentication authentication) {
        return accountService.me(authentication);
    }

    @PostMapping("change-username")
    @ResponseStatus(HttpStatus.OK)
    public void changeUsername(@RequestBody ChangeUsernameRequest changeUsernameRequest, Authentication authentication) {
        accountService.changeUsername(changeUsernameRequest, authentication);
    }

    @PostMapping("change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Authentication authentication) {
        accountService.changePassword(changePasswordRequest, authentication);
    }
}
