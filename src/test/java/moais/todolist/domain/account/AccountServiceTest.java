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

    @DisplayName("중복된 닉네임으로 회원가입을 할 수 없다.")
    @Test
    void duplicationUsername() {

    }

    @DisplayName("회원탈퇴를 한다.")
    @Test
    void delete() {
        //given
        Account account = Account
                .builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        Account savedAccount = accountRepository.save(account);

        //when
        accountService.delete(savedAccount);

        //then
        assertThat(accountRepository.findById(savedAccount.getId())).isEmpty();
    }

}