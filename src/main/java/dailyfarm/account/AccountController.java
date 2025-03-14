package dailyfarm.account;

import dailyfarm.account.entity.Account;
import dailyfarm.account.login.LoginRequest;
import dailyfarm.account.login.LoginResponse;
import dailyfarm.account.login.LoginService;
import dailyfarm.account.profile.ProfileResponse;
import dailyfarm.account.profile.ProfileService;
import dailyfarm.account.register.RegisterRequest;
import dailyfarm.account.register.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
public class AccountController<T extends Account, R extends ProfileResponse> {

    private final RegisterService<T> registerService;
    private final LoginService<T> loginService;
    private final ProfileService<T, R> profileService;

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody RegisterRequest request) {
        registerService.register(request);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }

    @GetMapping("profile")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getProfile(Authentication authentication) {
        return profileService.getProfile(authentication);
    }
}
