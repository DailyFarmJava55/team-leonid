package dailyfarm.business.register;

import dailyfarm.account.AccountFactory;
import dailyfarm.account.AccountRepository;
import dailyfarm.account.register.RegisterController;
import dailyfarm.business.entity.Business;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("business")
@RestController("BusinessRegisterController")
public class RegisterControllerImpl extends RegisterController<Business> {

    public RegisterControllerImpl(AccountRepository<Business> accountRepository, AccountFactory<Business> accountFactory, PasswordEncoder passwordEncoder) {
        super(accountRepository, accountFactory, passwordEncoder);
    }
}
