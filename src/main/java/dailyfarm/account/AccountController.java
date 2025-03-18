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
    public void register(@RequestBody RegisterRequest request) {
        accountService.register(request);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse login(@RequestBody LoginRequest request) {
        return accountService.login(request);
    }

    @GetMapping("profile")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponse getProfile(Authentication authentication) {
        return accountService.me(authentication);
    }

    @PostMapping("change-username")
    @ResponseStatus(HttpStatus.OK)
    public void changeUsername(@RequestBody ChangeUsernameRequest request, Authentication authentication) {
        accountService.changeUsername(request, authentication);
    }

    @PostMapping("change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        accountService.changePassword(request, authentication);
    }
}
