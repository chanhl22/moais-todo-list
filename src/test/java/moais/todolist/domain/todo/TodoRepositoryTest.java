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
    void findAll() {
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

        PageRequest pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "createdDateTime"));

        //when
        Page<Todo> todos = todoRepository.findByAccount(account, pageable);

        //then
        assertThat(todos).hasSize(3)
                .extracting("content")
                .containsExactly("hello4", "hello3", "hello2");
    }

    private Todo createTodo(String hello1, Account account) {
        return Todo.builder()
                .content(hello1)
                .status(TodoStatus.TODO)
                .account(account)
                .build();
    }

}