package dailyfarm.business;

import dailyfarm.account.AccountRepository;
import dailyfarm.business.entity.BusinessEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessRepository extends AccountRepository<BusinessEntity> {

}
