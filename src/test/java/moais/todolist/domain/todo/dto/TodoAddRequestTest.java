package moais.todolist.domain.todo.dto;

import moais.todolist.domain.account.Account;
import moais.todolist.domain.todo.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static moais.todolist.domain.todo.TodoStatus.TODO;
import static org.assertj.core.api.Assertions.assertThat;

class TodoAddRequestTest {

    @DisplayName("저장할 요청 정보를 Todo 엔티티로 변환한다.")
    @Test
    void toEntity() {
        //given
        Account account = Account.builder()
                .username("hello")
                .nickname("hello")
                .password("123")
                .role("USER")
                .build();
        TodoAddRequest request = TodoAddRequest.builder()
                .content("컨텐츠")
                .status(TODO)
                .build();

        //when
        Todo todo = request.toEntity(account);

        //then
        assertThat(todo)
                .extracting("content", "status")
                .contains("컨텐츠", TODO);
    }

}