package dailyfarm.business;

import dailyfarm.account.AccountRepository;
import dailyfarm.business.entity.Business;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends AccountRepository<Business> {

}
