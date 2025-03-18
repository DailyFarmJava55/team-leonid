package dailyfarm.account;

import dailyfarm.account.dto.*;
import dailyfarm.account.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

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
    public AccountResponse getProfile(Principal principal) {
        return accountService.getProfile(principal);
    }

    @PostMapping("change-username")
    @ResponseStatus(HttpStatus.OK)
    public void changeUsername(Principal principal, ChangeUsernameRequest request) {
        accountService.changeUsername(principal, request);
    }

    @PostMapping("change-password")
    @ResponseStatus(HttpStatus.OK)
    public void changePassword(Principal principal, ChangePasswordRequest request) {
        accountService.changePassword(principal, request);
    }
}
