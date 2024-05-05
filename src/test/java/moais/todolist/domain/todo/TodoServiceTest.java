package moais.todolist.domain.todo;

import moais.todolist.domain.account.Account;
import moais.todolist.domain.account.AccountRepository;
import moais.todolist.domain.exception.TodoException;
import moais.todolist.domain.paging.PagingRequest;
import moais.todolist.domain.todo.dto.TodoAddRequest;
import moais.todolist.domain.todo.dto.TodoResponse;
import moais.todolist.domain.todo.dto.TodoUpdateRequest;
import moais.todolist.domain.todo.dto.TodosResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static moais.todolist.domain.todo.TodoStatus.DONE;
import static moais.todolist.domain.todo.TodoStatus.IN_PROGRESS;
import static moais.todolist.domain.todo.TodoStatus.PENDING;
import static moais.todolist.domain.todo.TodoStatus.TODO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
                .nickname("hello")
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
        TodosResponse result = todoService.findTodoByAccount(account, request);

        //then
        assertThat(result.getTodos()).hasSize(3)
                .extracting("content", "nickname")
                .containsExactly(
                        tuple("hello4", "hello"),
                        tuple("hello3", "hello"),
                        tuple("hello2", "hello")
                );
    }

    @DisplayName("회원이 작성한 가장 최근의 todo를 조회한다.")
    @Test
    void findTop1ByAccount() {
        //given
        Account account = Account.builder()
                .username("hello")
                .nickname("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        Todo todo1 = createTodo("hello1", account);
        Todo todo2 = createTodo("hello2", account);
        Todo todo3 = createTodo("hello3", account);
        Todo todo4 = createTodo("hello4", account);
        todoRepository.saveAll(List.of(todo1, todo2, todo3, todo4));

        //when
        TodoResponse result = todoService.findTop1ByAccount(account);

        //then
        assertThat(result)
                .extracting("content", "nickname")
                .containsExactly("hello4", "hello");
    }

    @DisplayName("회원이 작성한 todo의 내용과 상태를 수정한다.")
    @Test
    void update() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        Todo todo = createTodo("hello1", account);
        Long id = todoRepository.save(todo).getId();

        TodoUpdateRequest request = TodoUpdateRequest.builder()
                .id(id)
                .content("hi")
                .status(IN_PROGRESS)
                .build();

        //when
        Long result = todoService.update(account, request);

        //then
        assertThat(todoRepository.findById(result).get())
                .extracting("content", "status")
                .containsExactly("hi", IN_PROGRESS);
    }

    @DisplayName("할 일 상태에서 대기 상태로 변경하면 예외가 발생한다.")
    @Test
    void todoToPending() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        Todo todo = createTodo("hello1", account, TODO);
        Long id = todoRepository.save(todo).getId();

        TodoUpdateRequest request = TodoUpdateRequest.builder()
                .id(id)
                .content("hi")
                .status(PENDING)
                .build();

        //when //then
        assertThatThrownBy(() -> todoService.update(account, request))
                .isInstanceOf(TodoException.class)
                .hasMessage("변경 조건에 적합하지 않아서 변경이 불가능합니다.");
    }

    @DisplayName("완료 상태에서 대기 상태로 변경하면 예외가 발생한다.")
    @Test
    void doneToPending() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        Todo todo = createTodo("hello1", account, DONE);
        Long id = todoRepository.save(todo).getId();

        TodoUpdateRequest request = TodoUpdateRequest.builder()
                .id(id)
                .content("hi")
                .status(PENDING)
                .build();

        //when //then
        assertThatThrownBy(() -> todoService.update(account, request))
                .isInstanceOf(TodoException.class)
                .hasMessage("변경 조건에 적합하지 않아서 변경이 불가능합니다.");
    }

    private Todo createTodo(String hello, Account account) {
        return Todo.builder()
                .content(hello)
                .status(TodoStatus.TODO)
                .account(account)
                .build();
    }

    private Todo createTodo(String hello, Account account, TodoStatus status) {
        return Todo.builder()
                .content(hello)
                .status(status)
                .account(account)
                .build();
    }

}