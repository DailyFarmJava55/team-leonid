package dailyfarm.customer;

import dailyfarm.customer.entity.Customer;
import dailyfarm.user.UserFactory;
import dailyfarm.user.UserRepository;
import dailyfarm.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends UserService<Customer> {

    public CustomerService(PasswordEncoder passwordEncoder, UserRepository<Customer> userRepository, UserFactory<Customer> userFactory) {
        super(passwordEncoder, userRepository, userFactory);
    }
}
