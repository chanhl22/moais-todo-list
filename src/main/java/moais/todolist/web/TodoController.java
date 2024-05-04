package moais.todolist.web;

import lombok.RequiredArgsConstructor;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.todo.TodoService;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static moais.todolist.util.SecurityUtils.getLoginManagerName;

@RequiredArgsConstructor
@RestController
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/todo/save")
    public ApiCommonResponse<Long> saveTodo(@RequestBody TodoAddRequest addRequest) {
        return ApiCommonResponse.ok(todoService.save(getLoginManagerName(), addRequest));
    }

    @GetMapping("/todo/list")
    @ResponseBody
    public ApiCommonResponse<List<TodoResponse>> findTodoList() {
        return ApiCommonResponse.ok(todoService.findAll());
    }

}
