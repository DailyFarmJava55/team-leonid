package dailyfarm.account.register;

import dailyfarm.account.AccountFactory;
import dailyfarm.account.AccountRepository;
import dailyfarm.account.entity.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j @RequiredArgsConstructor
public class RegisterService<T extends Account> {

    private final AccountRepository<T> accountRepository;
    private final AccountFactory<T> accountFactory;
    private final PasswordEncoder passwordEncoder;

    public void register(@RequestBody RegisterRequest request) {
        T account = accountFactory.create();
        account.setUsername(request.username());
        account.setEmail(request.email());
        account.setPhone(request.phone());
        account.setPassword(passwordEncoder.encode(request.password()));

        accountRepository.save(account);
    }
}
