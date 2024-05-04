package moais.todolist.domain.todo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moais.todolist.domain.account.Account;
import moais.todolist.domain.todo.Todo;
import moais.todolist.domain.todo.TodoStatus;

@Getter
@NoArgsConstructor
public class TodoAddRequest {

    private String content;

    private TodoStatus status;

    @Builder
    private TodoAddRequest(String content, TodoStatus status) {
        this.content = content;
        this.status = status;
    }

    public Todo toEntity(Account account) {
        return Todo.builder()
                .content(content)
                .status(status)
                .account(account)
                .build();
    }

}
