package dailyfarm.business;

import dailyfarm.account.AccountController;
import dailyfarm.account.login.LoginService;
import dailyfarm.account.profile.ProfileService;
import dailyfarm.account.register.RegisterService;
import dailyfarm.business.entity.Business;
import dailyfarm.business.profile.BusinessResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
public class BusinessController extends AccountController<Business, BusinessResponse> {

    public BusinessController(
        RegisterService<Business> registerService,
        LoginService<Business> loginService,
        ProfileService<Business, BusinessResponse> profileService
    ) {
        super(registerService, loginService, profileService);
    }
}
