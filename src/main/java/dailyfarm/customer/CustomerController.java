package dailyfarm.customer;

import dailyfarm.account.AccountController;
import dailyfarm.account.AccountService;
import dailyfarm.customer.entity.Customer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends AccountController<Customer> {

    public CustomerController(AccountService<Customer> accountService) {
        super(accountService);
    }
}
