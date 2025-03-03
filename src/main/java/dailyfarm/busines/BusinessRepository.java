package dailyfarm.busines;

import dailyfarm.busines.entity.Business;
import dailyfarm.user.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends UserRepository<Business> {

}
