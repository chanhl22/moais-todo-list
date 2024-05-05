package moais.todolist.domain.paging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

class PagingRequestTest {

    @DisplayName("페이징 요청를 DTO로 변환한다.")
    @Test
    void of() {
        //given
        Integer page = 0;
        Integer size = 5;

        //when
        PagingRequest request = PagingRequest.of(page, size);

        //then
        assertThat(request.getPage()).isEqualTo(0);
        assertThat(request.getSize()).isEqualTo(5);
    }

    @DisplayName("페이징 요청이 없는 경우 기본 페이징 값으로 변환한다.")
    @Test
    void defaultPagingInfo() {
        //given
        Integer page = null;
        Integer size = null;

        //when
        PagingRequest request = PagingRequest.of(page, size);

        //then
        assertThat(request.getPage()).isEqualTo(0);
        assertThat(request.getSize()).isEqualTo(5);
    }

    @DisplayName("페이징 DTO를 Pageable로 변환한다.")
    @Test
    void makePageable() {
        //given
        PagingRequest request = PagingRequest.of(0, 5);

        //when
        PageRequest pageRequest = request.makePageable();

        //then
        assertThat(pageRequest).isInstanceOf(Pageable.class);
    }

}