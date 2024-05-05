package moais.todolist.domain.account.dto;

import moais.todolist.domain.account.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AccountRequestTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("저장할 때 비밀번호는 암호화된다.")
    @Test
    void toEntity() {
        //given
        AccountRequest request = AccountRequest.builder()
                .username("hello")
                .password("123")
                .build();

        //when
        Account account = request.toEntity(passwordEncoder);

        //then
        assertThat(account.getPassword()).isNotEqualTo("123");
    }

}