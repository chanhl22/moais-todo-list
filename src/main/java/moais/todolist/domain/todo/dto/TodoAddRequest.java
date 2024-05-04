package moais.todolist.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moais.todolist.domain.account.Account;
import moais.todolist.domain.todo.Todo;
import moais.todolist.domain.todo.TodoStatus;

@Schema(description = "TODO 등록 요청 정보")
@Getter
@NoArgsConstructor
public class TodoAddRequest {

    @Schema(description = "TODO 내용", example = "리스트 개발")
    private String content;

    @Schema(description = "TODO 상태")
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
