package moais.todolist.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import moais.todolist.domain.todo.Todo;
import moais.todolist.domain.todo.TodoStatus;

import java.time.LocalDateTime;

@Schema(description = "TODO 응답 정보")
@Getter
public class TodoResponse {

    @Schema(description = "TODO id", example = "TODO id")
    private final Long id;

    @Schema(description = "TODO 내용", example = "리스트 개발")
    private final String content;

    @Schema(description = "TODO 상태")
    private final TodoStatus status;

    @Schema(description = "작성한 회원", example = "hello")
    private final String username;

    @Schema(description = "등록 시간")
    private final LocalDateTime createdDateTime;

    @Builder
    private TodoResponse(Long id, String content, TodoStatus status, String username, LocalDateTime createdDateTime) {
        this.id = id;
        this.content = content;
        this.status = status;
        this.username = username;
        this.createdDateTime = createdDateTime;
    }

    public static TodoResponse of(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .status(todo.getStatus())
                .username(todo.getAccount().getUsername())
                .createdDateTime(todo.getCreatedDateTime())
                .build();
    }

    public static TodoResponse empty() {
        return TodoResponse.builder()
                .build();
    }

}
