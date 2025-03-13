package dailyfarm.customer;

import dailyfarm.account.AccountFactory;
import dailyfarm.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactory implements AccountFactory<Customer> {

    @Override
    public Customer create() {
        return new Customer();
    }
}
