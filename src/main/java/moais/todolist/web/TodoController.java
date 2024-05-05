package moais.todolist.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.account.LoginAccount;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.todo.TodoService;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import moais.todolist.domain.todo.dto.TodoUpdateRequest;
import moais.todolist.domain.todo.dto.TodosResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "TODO", description = "TODO 리스트 API")
@Slf4j
@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "TODO 생성", description = "TODO를 생성합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @PostMapping("/todo/save")
    public ApiCommonResponse<Long> saveTodo(
            @AuthenticationPrincipal LoginAccount loginAccount, @Valid @RequestBody TodoAddRequest addRequest) {
        log.info("TODO 생성, 회원 ID = {}", loginAccount.getAccount().getId());
        log.debug("TODO 생성: content = {}, status = {}", addRequest.getContent(), addRequest.getStatus());
        return ApiCommonResponse.ok(todoService.save(loginAccount.getAccount(), addRequest));
    }

    @Operation(summary = "회원 TODO 리스트 조회", description = "회원이 작성한 TODO 리스트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @Parameters({
            @Parameter(name = "page", description = "시작 페이지 번호", example = "0"),
            @Parameter(name = "size", description = "조회할 사이즈", example = "5")
    })
    @GetMapping("/todo/list")
    @ResponseBody
    public ApiCommonResponse<TodosResponse> findTodoList(
            @AuthenticationPrincipal LoginAccount loginAccount,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        log.info("TODO 리스트 조회, 회원 ID = {}", loginAccount.getAccount().getId());
        log.debug("TODO 리스트 조회: page = {}, size = {}", page, size);
        return ApiCommonResponse.ok(
                todoService.findTodoByAccount(loginAccount.getAccount(), PagingRequest.of(page, size)));
    }

    @Operation(summary = "회원의 가장 최근 TODO 조회", description = "회원이 작성한 가장 최근 TODO를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @GetMapping("/todo/latest")
    @ResponseBody
    public ApiCommonResponse<TodoResponse> findTodoLatest(
            @AuthenticationPrincipal LoginAccount loginAccount) {
        log.info("최근 TODO 조회, 회원 ID = {}", loginAccount.getAccount().getId());
        return ApiCommonResponse.ok(todoService.findTop1ByAccount(loginAccount.getAccount()));
    }

    @Operation(summary = "TODO 수정", description = "TODO를 수정합니다.")
    @ApiResponse(responseCode = "200", description = "성공", useReturnTypeSchema = true)
    @PostMapping("/todo/update")
    @ResponseBody
    public ApiCommonResponse<Long> update(
            @AuthenticationPrincipal LoginAccount loginAccount, @Valid @RequestBody TodoUpdateRequest updateRequest) {
        log.info("TODO 수정, 회원 ID = {}", loginAccount.getAccount().getId());
        log.debug("TODO 수정: content = {}, status = {}", updateRequest.getContent(), updateRequest.getStatus());
        return ApiCommonResponse.ok(todoService.update(loginAccount.getAccount(), updateRequest));
    }

}
