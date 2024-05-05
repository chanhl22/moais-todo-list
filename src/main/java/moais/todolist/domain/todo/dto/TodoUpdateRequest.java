package moais.todolist.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import moais.todolist.domain.todo.TodoStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Schema(description = "TODO 수정 요청 정보")
@Getter
@NoArgsConstructor
public class TodoUpdateRequest {

    @Schema(description = "TODO id", example = "3")
    @Positive(message = "id는 필수입니다.")
    private Long id;

    @Schema(description = "TODO 내용", example = "리스트 개발")
    @NotBlank(message = "content는 필수입니다.")
    private String content;

    @Schema(description = "TODO 상태")
    @NotNull(message = "유효하지 않은 status입니다.")
    private TodoStatus status;

    @Builder
    private TodoUpdateRequest(Long id, String content, TodoStatus status) {
        this.id = id;
        this.content = content;
        this.status = status;
    }

}
