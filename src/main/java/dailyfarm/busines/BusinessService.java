package dailyfarm.busines;

import dailyfarm.busines.entity.Business;
import dailyfarm.user.UserFactory;
import dailyfarm.user.UserRepository;
import dailyfarm.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BusinessService extends UserService<Business> {

    public BusinessService(PasswordEncoder passwordEncoder, UserRepository<Business> userRepository, UserFactory<Business> userFactory) {
        super(passwordEncoder, userRepository, userFactory);
    }
}
