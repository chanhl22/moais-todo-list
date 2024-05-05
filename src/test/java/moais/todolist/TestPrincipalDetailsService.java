package moais.todolist;

import moais.todolist.domain.account.Account;
import moais.todolist.domain.account.LoginAccount;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TestPrincipalDetailsService implements UserDetailsService {

    public static final String USERNAME = "tester";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals(USERNAME)) {
            return new LoginAccount(getAccount());
        }
        return null;
    }

    private Account getAccount() {
        return Account.builder()
                .id(1L)
                .username(USERNAME)
                .password("1234")
                .role("USER")
                .build();
    }

}
