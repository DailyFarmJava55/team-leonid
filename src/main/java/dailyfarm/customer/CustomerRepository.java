package dailyfarm.customer;

import dailyfarm.customer.entity.Customer;
import dailyfarm.user.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends UserRepository<Customer> {

}
