package dailyfarm.busines;

import dailyfarm.busines.entity.Business;
import dailyfarm.user.UserFactory;
import org.springframework.stereotype.Component;

@Component
public class BusinessFactory implements UserFactory<Business> {

    @Override
    public Business create() {
        return new Business();
    }
}
