package moais.todolist.domain.todo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.account.Account;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
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

    @Transactional
    public Long save(Account account, TodoAddRequest addRequest) {
        return todoRepository.save(addRequest.toEntity(account)).getId();
    }

    public List<TodoResponse> findTodoByAccount(Account account, PagingRequest pagingRequest) {
        return todoRepository.findByAccount(account, pagingRequest.makePageable()).stream()
                .map(TodoResponse::of)
                .collect(Collectors.toList());
    }

}


