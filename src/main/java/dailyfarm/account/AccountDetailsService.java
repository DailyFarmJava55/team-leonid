package dailyfarm.account;

import dailyfarm.account.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

public class AccountDetailsService<T extends Account> implements UserDetailsService {

    private final AccountRepository<T> accountRepository;

    public AccountDetailsService(AccountRepository<T> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByUsernameOrEmailOrPhone(username)
            .map(account ->
                new User(username, account.getPassword(), getAuthorities(account)))
            .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    private Collection<GrantedAuthority> getAuthorities(T account) {
        return account.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
