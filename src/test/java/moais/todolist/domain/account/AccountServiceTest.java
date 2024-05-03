package moais.todolist.domain.account;

import moais.todolist.domain.account.dto.AccountRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        accountRepository.deleteAllInBatch();
    }

    @DisplayName("회원가입을 한다.")
    @Test
    void createNew() {
        //given
        AccountRequest request = AccountRequest.builder()
                .username("hello")
                .password("123")
                .role("ADMIN")
                .build();

        //when
        Long createdAccountId = accountService.createNew(request);

        //then
        assertThat(createdAccountId).isNotNull();
    }

}