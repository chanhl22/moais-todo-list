package moais.todolist.domain.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @DisplayName("회원가입된 회원을 조회한다.")
    @Test
    void findByUsername() {
        //given
        String username = "hello";
        Account account = Account.builder()
                .username(username)
                .password("123")
                .role("ADMIN")
                .build();
        Account save = accountRepository.save(account);

        //when
        Optional<Account> findAccount = accountRepository.findByUsername(username);

        //then
        assertThat(findAccount.get().getUsername()).isEqualTo(username);
    }

}