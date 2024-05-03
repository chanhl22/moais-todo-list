package moais.todolist.web;

import lombok.RequiredArgsConstructor;
import moais.todolist.domain.account.AccountService;
import moais.todolist.domain.account.dto.AccountRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account/{username}/{password}/{role}")
    @ResponseBody
    public Long createAccount(@PathVariable String username, @PathVariable String password, @PathVariable String role) {
        return accountService.createNew(AccountRequest.of(username, password, role));
    }

}
