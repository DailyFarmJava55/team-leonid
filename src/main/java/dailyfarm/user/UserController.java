package dailyfarm.user;

import dailyfarm.user.dto.LoginRequest;
import dailyfarm.user.dto.RegisterRequest;
import dailyfarm.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequiredArgsConstructor
public abstract class UserController<T extends User> {

    private final UserService<T> userService;

    @PostMapping(path = "register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestBody RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PostMapping(path = "login")
    @ResponseStatus(HttpStatus.OK)
    void login(@RequestBody LoginRequest loginRequest) {
        userService.login(loginRequest);
    }

    @PostMapping(path = "logout")
    @ResponseStatus(HttpStatus.OK)
    void logout() {
        userService.logout();
    }

    @PostMapping(path = "change-password")
    @ResponseStatus(HttpStatus.OK)
    void changePassword(@RequestBody String password) {
        userService.changePassword(password);
    }

    @PostMapping(path = "reset-password")
    @ResponseStatus(HttpStatus.OK)
    void resetPassword() {
        userService.resetPassword();
    }

    @PostMapping(path = "refresh-token")
    @ResponseStatus(HttpStatus.OK)
    void refreshToken(@RequestBody String refreshToken) {
        userService.refreshToken(refreshToken);
    }
}
