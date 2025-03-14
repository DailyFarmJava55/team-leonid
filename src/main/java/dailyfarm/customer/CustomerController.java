package dailyfarm.customer;

import dailyfarm.account.AccountController;
import dailyfarm.account.login.LoginService;
import dailyfarm.account.profile.ProfileService;
import dailyfarm.account.register.RegisterService;
import dailyfarm.customer.entity.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends AccountController<Customer> {

    public CustomerController(
        RegisterService<Customer> registerService,
        LoginService<Customer> loginService,
        ProfileService<Customer> profileService
    ) {
        super(registerService, loginService, profileService);
    }
}
