package dailyfarm.account;

import dailyfarm.account.entity.Account;

public interface AccountFactory<T extends Account> {

    T create();
}
