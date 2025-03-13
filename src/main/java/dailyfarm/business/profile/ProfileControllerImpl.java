package dailyfarm.business.profile;

import dailyfarm.account.AccountRepository;
import dailyfarm.account.profile.ProfileController;
import dailyfarm.business.entity.Business;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("business")
@RestController("BusinessProfileController")
public class ProfileControllerImpl extends ProfileController<Business> {

    public ProfileControllerImpl(AccountRepository<Business> accountRepository) {
        super(accountRepository);
    }
}
