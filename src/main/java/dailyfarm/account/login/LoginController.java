package dailyfarm.account.login;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static dailyfarm.authentication.JwtTokenProvider.generateAccessToken;

@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        authentication = authenticationManager.authenticate(authentication);

        Long currentTimeMillis = System.currentTimeMillis();

        String accessToken = generateAccessToken(authentication, currentTimeMillis);
        String refreshToken = "success";

        return new LoginResponse(accessToken, refreshToken);
    }

    // TODO: Postman (Authorization)
    // TODO: Revoke Refresh Tokens
    // TODO: Refresh Token Rotation
}
