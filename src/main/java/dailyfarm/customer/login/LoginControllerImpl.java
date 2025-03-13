package dailyfarm.customer.login;

import dailyfarm.account.login.LoginController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("customer")
@RestController("CustomerLoginController")
public class LoginControllerImpl extends LoginController {

    public LoginControllerImpl(
        @Qualifier("CustomerAuthenticationManager")
        AuthenticationManager authenticationManager
    ) {
        super(authenticationManager);
    }
}
