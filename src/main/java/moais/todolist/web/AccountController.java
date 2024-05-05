package moais.todolist.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.account.AccountService;
import moais.todolist.domain.account.LoginAccount;
import moais.todolist.domain.account.dto.AccountRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Tag(name = "회원", description = "회원 API")
@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "회원가입", description = "회원에 가입합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @PostMapping("/account/signup")
    @ResponseBody
    public ApiCommonResponse<Long> createAccount(@RequestBody AccountRequest addRequest) {
        return ApiCommonResponse.ok(accountService.createNew(addRequest));
    }

    @Operation(summary = "회원탈퇴", description = "회원에서 탈퇴합니다.<br>" +
            "기존에 작성한 모든 정보와 회원 정보가 삭제됩니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @DeleteMapping("/account/delete")
    @ResponseBody
    public ApiCommonResponse<Object> deleteAccount(@AuthenticationPrincipal LoginAccount loginAccount) {
        accountService.delete(loginAccount.getAccount());
        return ApiCommonResponse.ok();
    }

}
