package dailyfarm.customer;

import dailyfarm.customer.entity.Customer;
import dailyfarm.user.UserController;
import dailyfarm.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController extends UserController<Customer> {

    public CustomerController(UserService<Customer> userService) {
        super(userService);
    }
}
