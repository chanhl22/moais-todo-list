package moais.todolist.domain.account;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

@Getter
public class LoginAccount extends User {

    private final Account account;

    public LoginAccount(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole()));
        this.account = account;
    }

}
