package moais.todolist.domain.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PagingResponseTest {

    @DisplayName("페이징 정보를 응답 DTO로 변환한다.")
    @Test
    void of() {
        //given
        long totalCount = 0L;
        int pageNumber = 1;
        int totalPages = 5;
        boolean hasNext = true;

        //when
        PagingResponse result = PagingResponse.of(totalCount, pageNumber, totalPages, hasNext);

        //then
        assertThat(result.getTotalCount()).isEqualTo(0L);
        assertThat(result.getPageNumber()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(5);
        assertThat(result.isHasNext()).isTrue();
    }

}