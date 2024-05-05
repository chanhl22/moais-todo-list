package moais.todolist.domain.todo.dto;

import moais.todolist.domain.paging.PagingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static moais.todolist.domain.todo.TodoStatus.TODO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class TodosResponseTest {

    @DisplayName("Todo 엔티티를 리스트 응답 DTO로 변환한다.")
    @Test
    void of() {
        //given
        PagingResponse page = PagingResponse.builder()
                .totalCount(10)
                .build();

        TodoResponse response1 = TodoResponse.builder()
                .content("컨텐츠1")
                .status(TODO)
                .nickname("hello")
                .build();
        TodoResponse response2 = TodoResponse.builder()
                .content("컨텐츠2")
                .status(TODO)
                .nickname("hello")
                .build();
        List<TodoResponse> list = List.of(response1, response2);

        //when
        TodosResponse result = TodosResponse.of(page, list);

        //then
        assertThat(result.getTodos()).hasSize(2)
                .extracting("content", "status", "nickname")
                .containsExactlyInAnyOrder(
                        tuple("컨텐츠1", TODO, "hello"),
                        tuple("컨텐츠2", TODO, "hello")
                );
        assertThat(result.getPage().getTotalCount()).isEqualTo(10);
    }

}