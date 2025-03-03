package dailyfarm.busines;

import dailyfarm.busines.entity.Business;
import dailyfarm.user.UserController;
import dailyfarm.user.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("business")
public class BusinessController extends UserController<Business> {

    public BusinessController(UserService<Business> userService) {
        super(userService);
    }
}
