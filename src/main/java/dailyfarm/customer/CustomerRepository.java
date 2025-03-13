package dailyfarm.customer;

import dailyfarm.account.AccountRepository;
import dailyfarm.customer.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends AccountRepository<Customer> {

}
