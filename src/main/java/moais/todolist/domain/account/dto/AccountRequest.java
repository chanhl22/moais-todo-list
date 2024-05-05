package moais.todolist.domain.account.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import moais.todolist.domain.account.Account;
import org.springframework.security.crypto.password.PasswordEncoder;

@Schema(description = "회원 등록 요청 정보")
@Getter
public class AccountRequest {

    private static final String ROLE = "USER";

    @Schema(description = "회원 닉네임", example = "hello")
    private final String username;

    @Schema(description = "비밀번호", example = "123")
    private final String password;

    @Builder
    private AccountRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Account toEntity(PasswordEncoder passwordEncoder) {
        return Account.builder()
                .username(username)
                .nickname(username)
                .password(passwordEncoder.encode(password))
                .role(ROLE)
                .build();
    }

}

