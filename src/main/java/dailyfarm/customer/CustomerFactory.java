package dailyfarm.customer;

import dailyfarm.customer.entity.Customer;
import dailyfarm.user.UserFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactory implements UserFactory<Customer> {

    @Override
    public Customer create() {
        return new Customer();
    }
}
