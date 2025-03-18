package dailyfarm.business;

import dailyfarm.account.AccountController;
import dailyfarm.account.AccountService;
import dailyfarm.business.entity.Business;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
public class BusinessController extends AccountController<Business> {

    public BusinessController(AccountService<Business> accountService) {
        super(accountService);
    }
}
