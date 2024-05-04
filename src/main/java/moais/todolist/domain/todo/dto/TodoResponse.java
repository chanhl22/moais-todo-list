package moais.todolist.domain.todo.dto;

import lombok.Builder;
import lombok.Getter;
import moais.todolist.domain.todo.Todo;
import moais.todolist.domain.todo.TodoStatus;

import java.time.LocalDateTime;

@Getter
public class TodoResponse {

    private final String content;

    private final TodoStatus status;

    private final String username;

    private final LocalDateTime createdDateTime;

    @Builder
    private TodoResponse(String content, TodoStatus status, String username, LocalDateTime createdDateTime) {
        this.content = content;
        this.status = status;
        this.username = username;
        this.createdDateTime = createdDateTime;
    }

    public static TodoResponse of(Todo todo) {
        return TodoResponse.builder()
                .content(todo.getContent())
                .status(todo.getStatus())
                .username(todo.getAccount().getUsername())
                .createdDateTime(todo.getCreatedDateTime())
                .build();
    }

}
