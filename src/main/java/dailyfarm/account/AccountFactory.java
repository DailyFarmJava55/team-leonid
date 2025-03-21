package dailyfarm.account;

import dailyfarm.account.entity.AccountEntity;

public interface AccountFactory<T extends AccountEntity> {

    T create();
}
