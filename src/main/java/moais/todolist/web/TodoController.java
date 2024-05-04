package moais.todolist.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.account.LoginAccount;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.todo.TodoService;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import moais.todolist.domain.todo.dto.TodoUpdateRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "TODO", description = "TODO 리스트 API")
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "TODO 생성", description = "TODO를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @PostMapping("/todo/save")
    public ApiCommonResponse<Long> saveTodo(
            @AuthenticationPrincipal LoginAccount loginAccount, @RequestBody TodoAddRequest addRequest) {
        return ApiCommonResponse.ok(todoService.save(loginAccount.getAccount(), addRequest));
    }

    @Operation(summary = "회원 TODO 리스트 조회", description = "회원이 작성한 TODO 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @GetMapping("/todo/list")
    @ResponseBody
    public ApiCommonResponse<List<TodoResponse>> findTodoList(
            @AuthenticationPrincipal LoginAccount loginAccount, @RequestBody PagingRequest pagingRequest) {
        return ApiCommonResponse.ok(todoService.findTodoByAccount(loginAccount.getAccount(), pagingRequest));
    }

    @Operation(summary = "회원의 가장 최근 TODO 조회", description = "회원이 작성한 가장 최근 TODO를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @GetMapping("/todo/latest")
    @ResponseBody
    public ApiCommonResponse<TodoResponse> findTodoLatest(
            @AuthenticationPrincipal LoginAccount loginAccount) {
        return ApiCommonResponse.ok(todoService.findTop1ByAccount(loginAccount.getAccount()));
    }

    @Operation(summary = "TODO 수정", description = "TODO를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @PostMapping("/todo/update")
    @ResponseBody
    public ApiCommonResponse<Long> update(
            @AuthenticationPrincipal LoginAccount loginAccount, @RequestBody TodoUpdateRequest updateRequest) {
        return ApiCommonResponse.ok(todoService.update(loginAccount.getAccount(), updateRequest));
    }

}
