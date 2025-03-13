package dailyfarm.business;

import dailyfarm.account.AccountDetailsService;
import dailyfarm.account.AccountRepository;
import dailyfarm.business.entity.Business;
import org.springframework.stereotype.Service;

@Service
public class BusinessDetailsService extends AccountDetailsService<Business> {

    public BusinessDetailsService(AccountRepository<Business> accountRepository) {
        super(accountRepository);
    }
}
