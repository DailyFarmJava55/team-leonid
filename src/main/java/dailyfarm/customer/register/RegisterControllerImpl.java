package dailyfarm.customer.register;

import dailyfarm.account.AccountFactory;
import dailyfarm.account.AccountRepository;
import dailyfarm.account.register.RegisterController;
import dailyfarm.customer.entity.Customer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("customer")
@RestController("CustomerRegisterController")
public class RegisterControllerImpl extends RegisterController<Customer> {

    public RegisterControllerImpl(AccountRepository<Customer> accountRepository, AccountFactory<Customer> accountFactory, PasswordEncoder passwordEncoder) {
        super(accountRepository, accountFactory, passwordEncoder);
    }
}
