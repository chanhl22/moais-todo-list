package moais.todolist.domain.todo;

import moais.todolist.domain.account.Account;
import moais.todolist.domain.account.AccountRepository;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
class TodoServiceTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TodoService todoService;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();
    }

    @DisplayName("todo를 저장한다.")
    @Test
    void save() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        TodoAddRequest request = TodoAddRequest.builder()
                .content("hello1")
                .status(TodoStatus.TODO)
                .build();

        //when
        Long todoId = todoService.save(account, request);

        //then
        assertThat(todoId).isNotNull();
    }

    @DisplayName("회원이 작성한 todo 리스트를 등록순으로 조회한다.")
    @Test
    void findTodoByAccount() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        Todo todo1 = createTodo("hello1", account);
        Todo todo2 = createTodo("hello2", account);
        Todo todo3 = createTodo("hello3", account);
        Todo todo4 = createTodo("hello4", account);
        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4));

        PagingRequest request = PagingRequest.builder()
                .page(0)
                .size(3)
                .build();

        //when
        List<TodoResponse> result = todoService.findTodoByAccount(account, request);

        //then
        assertThat(result).hasSize(3)
                .extracting("content", "username")
                .containsExactly(
                        tuple("hello4", "hello"),
                        tuple("hello3", "hello"),
                        tuple("hello2", "hello")
                );
    }

    private Todo createTodo(String hello1, Account account) {
        return Todo.builder()
                .content(hello1)
                .status(TodoStatus.TODO)
                .account(account)
                .build();
    }

}