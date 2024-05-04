package moais.todolist.domain.todo;

import moais.todolist.domain.account.Account;
import moais.todolist.domain.account.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private AccountRepository accountRepository;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();
    }

    @DisplayName("회원의 todo 리스트를 조회한다.")
    @Test
    void findByAccount() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        createTodo("hello1", account);
        createTodo("hello2", account);
        createTodo("hello3", account);
        createTodo("hello4", account);


        PageRequest pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDateTime"));

        //when
        Page<Todo> todos = todoRepository.findByAccount(account, pageable);

        //then
        assertThat(todos).hasSize(3)
                .extracting("content")
                .containsExactly("hello4", "hello3", "hello2");
    }

    @DisplayName("회원의 가장 최근의 todo를 조회한다.")
    @Test
    void findTop1ByAccount() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        createTodo("hello1", account);
        createTodo("hello2", account);
        createTodo("hello3", account);
        createTodo("hello4", account);

        //when
        Optional<Todo> todo = todoRepository.findTop1ByAccountOrderByCreatedDateTimeDesc(account);

        //then
        assertThat(todo.get().getContent()).isEqualTo("hello4");
        assertThat(todo.get().getAccount().getUsername()).isEqualTo("hello");
    }

    @DisplayName("회원이 수정하기 위한 todo를 조회한다.")
    @Test
    void findByAccountAndId() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        Todo todo = createTodo("hello1", account);

        //when
        Optional<Todo> result = todoRepository.findByAccountAndId(account, todo.getId());

        //then
        assertThat(result.get().getContent()).isEqualTo("hello1");
    }

    private Todo createTodo(String hello1, Account account) {
        Todo todo = Todo.builder()
                .content(hello1)
                .status(TodoStatus.TODO)
                .account(account)
                .build();
        return todoRepository.save(todo);
    }

}