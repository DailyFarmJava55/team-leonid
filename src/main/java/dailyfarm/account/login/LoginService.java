package dailyfarm.account.login;

import dailyfarm.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static dailyfarm.jwt.JwtTokenProvider.authenticationToJwtToken;

@Slf4j @RequiredArgsConstructor
public class LoginService<T extends Account> {

    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        log.info(request.toString());

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.username(), request.password());

        authentication = authenticationManager.authenticate(authentication);

        Long currentTimeMillis = System.currentTimeMillis();

        String accessToken = authenticationToJwtToken(authentication, currentTimeMillis);
        String refreshToken = "success";

        return new LoginResponse(accessToken, refreshToken);
    }
}
