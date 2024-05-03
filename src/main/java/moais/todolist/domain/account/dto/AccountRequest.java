package moais.todolist.domain.account.dto;

import lombok.Builder;
import lombok.Getter;
import moais.todolist.domain.account.Account;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class AccountRequest {

    private final String username;

    private final String password;

    private final String role;

    @Builder
    private AccountRequest(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static AccountRequest of(String username, String password, String role) {
        return AccountRequest.builder()
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .build();
    }

}

