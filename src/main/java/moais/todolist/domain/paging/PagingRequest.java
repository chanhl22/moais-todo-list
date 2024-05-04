package moais.todolist.domain.paging;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Schema(description = "페이지 정보")
@Getter
@NoArgsConstructor
public class PagingRequest {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 5;

    @Schema(description = "시작 페이지 번호", example = "0")
    private int page = DEFAULT_PAGE_NUMBER;

    @Schema(description = "조회할 사이즈", example = "5")
    private int size = DEFAULT_PAGE_SIZE;

    @Builder
    private PagingRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public PageRequest makePageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateTime"));
    }

}
