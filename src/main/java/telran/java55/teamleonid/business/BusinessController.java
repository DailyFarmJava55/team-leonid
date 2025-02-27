package telran.java55.teamleonid.business;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import telran.java55.teamleonid.business.dto.BusinessDto;
import telran.java55.teamleonid.business.dto.LoginDto;
import telran.java55.teamleonid.business.dto.RegisterDto;
import telran.java55.teamleonid.business.dto.TokenDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/business")
public class BusinessController {

    private final BusinessService businessService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "auth/register")
    BusinessDto register(@RequestBody RegisterDto registerDto) {
        return businessService.register(registerDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "auth/login")
    TokenDto login(@RequestBody LoginDto loginDto) {
        return businessService.login(loginDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "auth/logout")
    void logout(@RequestBody String refreshToken) {
        businessService.logout(refreshToken);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(path = "auth/refresh-token")
    TokenDto refreshToken(@RequestBody String refreshToken) {
        return businessService.refreshToken(refreshToken);
    }

    // TODO: change-password
    // TODO: reset-password
    // TODO: profile
}
