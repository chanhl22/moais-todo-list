package moais.todolist.domain.account;

import moais.todolist.domain.account.dto.AccountRequest;
import moais.todolist.domain.exception.TodoException;
import moais.todolist.domain.todo.Todo;
import moais.todolist.domain.todo.TodoRepository;
import moais.todolist.domain.todo.TodoStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void tearDown() {
        todoRepository.deleteAllInBatch();
        accountRepository.deleteAllInBatch();
    }

    @DisplayName("회원가입을 한다.")
    @Test
    void createNew() {
        //given
        AccountRequest request = AccountRequest.builder()
                .username("hello")
                .password("123")
                .build();

        //when
        Long createdAccountId = accountService.createNew(request);

        //then
        assertThat(createdAccountId).isNotNull();
    }

    @DisplayName("중복된 이름으로 회원가입을 하는 경우 예외가 발생한다.")
    @Test
    void isExistUsername() {
        //given
        Account account = Account.builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        accountRepository.save(account);

        AccountRequest request = AccountRequest.builder()
                .username("hello")
                .password("123")
                .build();

        //when //then
        assertThatThrownBy(() -> accountService.createNew(request))
                .isInstanceOf(TodoException.class)
                .hasMessage("이미 등록된 회원입니다.");
    }

    @DisplayName("회원탈퇴를 한다.")
    @Test
    void delete() {
        //given
        Account account = Account
                .builder()
                .username("hello")
                .password("123")
                .role("USER")
                .build();
        Account savedAccount = accountRepository.save(account);

        createTodo("hello1", account);
        createTodo("hello2", account);
        createTodo("hello3", account);
        createTodo("hello4", account);

        //when
        accountService.delete(savedAccount);

        //then
        assertThat(accountRepository.findById(savedAccount.getId())).isEmpty();
        assertThat(todoRepository.findByAccount(account)).isEmpty();
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