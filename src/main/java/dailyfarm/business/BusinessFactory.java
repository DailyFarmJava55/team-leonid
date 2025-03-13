package dailyfarm.business;

import dailyfarm.account.AccountFactory;
import dailyfarm.business.entity.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessFactory implements AccountFactory<Business> {

    @Override
    public Business create() {
        return new Business();
    }
}
