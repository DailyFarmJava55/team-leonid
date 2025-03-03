package dailyfarm.user;

import dailyfarm.user.entity.User;

public interface UserFactory<T extends User> {

    T create();
}
