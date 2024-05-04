package moais.todolist.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moais.todolist.domain.account.AccountService;
import moais.todolist.domain.account.dto.AccountRequest;
import org.springframework.stereotype.Controller;
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
    @PostMapping("/account/save")
    @ResponseBody
    public Long createAccount(@RequestBody AccountRequest addRequest) {
        return accountService.createNew(addRequest);
    }

}
