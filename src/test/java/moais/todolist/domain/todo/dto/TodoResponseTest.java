package moais.todolist.domain.todo.dto;

import moais.todolist.domain.account.Account;
import moais.todolist.domain.todo.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static moais.todolist.domain.todo.TodoStatus.TODO;
import static org.assertj.core.api.Assertions.assertThat;

class TodoResponseTest {

    @DisplayName("Todo 엔티티를 응답 DTO로 변환한다.")
    @Test
    void of() {
        //given
        Account account = Account.builder()
                .username("hello")
                .nickname("hello")
                .password("123")
                .role("USER")
                .build();

        Todo todo = Todo.builder()
                .id(1L)
                .content("컨텐츠")
                .status(TODO)
                .account(account)
                .build();

        //when
        TodoResponse result = TodoResponse.of(todo);

        //then
        assertThat(result)
                .extracting("content", "status", "nickname")
                .contains("컨텐츠", TODO, "hello");
    }

    @DisplayName("Todo 엔티티를 빈 응답 DTO로 변환한다.")
    @Test
    void empty() {
        //when
        TodoResponse result = TodoResponse.empty();

        //then
        assertThat(result)
                .extracting("content", "status", "nickname")
                .contains(null, null, null);
    }

}