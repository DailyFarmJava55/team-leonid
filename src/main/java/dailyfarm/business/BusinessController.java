package dailyfarm.business;

import dailyfarm.account.AccountController;
import dailyfarm.account.AccountService;
import dailyfarm.business.entity.BusinessEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
public class BusinessController extends AccountController<BusinessEntity> {

    public BusinessController(AccountService<BusinessEntity> accountService) {
        super(accountService);
    }
}
