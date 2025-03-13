package dailyfarm.customer.profile;

import dailyfarm.account.AccountRepository;
import dailyfarm.account.profile.ProfileController;
import dailyfarm.customer.entity.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("customer")
@RestController("CustomerProfileController")
public class ProfileControllerImpl extends ProfileController<Customer> {

    public ProfileControllerImpl(AccountRepository<Customer> accountRepository) {
        super(accountRepository);
    }
}
