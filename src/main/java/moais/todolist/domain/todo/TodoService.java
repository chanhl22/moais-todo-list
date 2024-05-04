package moais.todolist.domain.todo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.account.Account;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.policy.StatusPolicy;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import moais.todolist.domain.todo.dto.TodoUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final StatusPolicy statusPolicy;

    @Transactional
    public Long save(Account account, TodoAddRequest addRequest) {
        return todoRepository.save(addRequest.toEntity(account)).getId();
    }

    public List<TodoResponse> findTodoByAccount(Account account, PagingRequest pagingRequest) {
        return todoRepository.findByAccount(account, pagingRequest.makePageable()).stream()
                .map(TodoResponse::of)
                .collect(Collectors.toList());
    }

    public TodoResponse findTop1ByAccount(Account account) {
        return todoRepository.findTop1ByAccountOrderByCreatedDateTimeDesc(account)
                .map(TodoResponse::of)
                .orElseGet(TodoResponse::empty);
    }

    @Transactional
    public Long update(Account account, TodoUpdateRequest updateRequest) {
        Todo todo = todoRepository.findByAccountAndId(account, updateRequest.getId())
                .orElseThrow(IllegalArgumentException::new);

        updateTodo(updateRequest, todo);

        return todo.getId();
    }

    private void updateTodo(TodoUpdateRequest updateRequest, Todo todo) {
        if (statusPolicy.canStatusModify(todo.getStatus(), updateRequest.getStatus())) {
            todo.update(updateRequest.getContent(), updateRequest.getStatus());
        }
    }

}


