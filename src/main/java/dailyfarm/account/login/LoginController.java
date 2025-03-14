package dailyfarm.account.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static dailyfarm.jwt.JwtTokenProvider.authenticationToJwtToken;

@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        authentication = authenticationManager.authenticate(authentication);

        Long currentTimeMillis = System.currentTimeMillis();

        String accessToken = authenticationToJwtToken(authentication, currentTimeMillis);
        String refreshToken = "success";

        return new LoginResponse(accessToken, refreshToken);
    }

    // TODO: Refresh Token Rotation

    // TODO: Revoke Refresh Tokens

    // TODO: Postman (Authorization)
}
