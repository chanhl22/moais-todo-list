package moais.todolist.domain.account;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        accountRepository.deleteAllInBatch();
    }

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
        accountRepository.save(account);

        //when
        Optional<Account> findAccount = accountRepository.findByUsername(username);

        //then
        assertThat(findAccount.get().getUsername()).isEqualTo(username);
    }

}