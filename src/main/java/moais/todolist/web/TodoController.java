package moais.todolist.web;

import lombok.RequiredArgsConstructor;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.todo.TodoService;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import moais.todolist.domain.account.LoginAccount;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todo/save")
    public ApiCommonResponse<Long> saveTodo(
            @AuthenticationPrincipal LoginAccount loginAccount, @RequestBody TodoAddRequest addRequest) {
        return ApiCommonResponse.ok(todoService.save(loginAccount.getAccount(), addRequest));
    }

    @GetMapping("/todo/list")
    @ResponseBody
    public ApiCommonResponse<List<TodoResponse>> findTodoList(
            @AuthenticationPrincipal LoginAccount loginAccount, @RequestBody PagingRequest pagingRequest) {
        return ApiCommonResponse.ok(todoService.findTodoByAccount(loginAccount.getAccount(), pagingRequest));
    }

}
