package dailyfarm.customer;

import dailyfarm.account.AccountDetailsService;
import dailyfarm.account.AccountRepository;
import dailyfarm.customer.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsService extends AccountDetailsService<Customer> {

    public CustomerDetailsService(AccountRepository<Customer> accountRepository) {
        super(accountRepository);
    }
}
