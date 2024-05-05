package moais.todolist.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import moais.todolist.domain.paging.PagingResponse;

import java.util.List;

@Schema(description = "TODO 응답 정보")
@Getter
public class TodosResponse {

    @Schema(description = "페이징 정보")
    private final PagingResponse page;

    @Schema(description = "TODO 응답 정보")
    private final List<TodoResponse> todos;

    @Builder
    private TodosResponse(PagingResponse page, List<TodoResponse> todos) {
        this.page = page;
        this.todos = todos;
    }

    public static TodosResponse of(PagingResponse page, List<TodoResponse> todos) {
        return TodosResponse.builder()
                .page(page)
                .todos(todos)
                .build();
    }

}
