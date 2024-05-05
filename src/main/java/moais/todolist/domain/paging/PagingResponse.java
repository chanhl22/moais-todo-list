package moais.todolist.domain.paging;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "페이징 정보")
@Getter
@NoArgsConstructor
public class PagingResponse {

    @Schema(description = "전체 데이터 수")
    private long totalCount;

    @Schema(description = "페이지 번호")
    private int pageNumber;

    @Schema(description = "전체 페이지 번호")
    private int totalPages;

    @Schema(description = "다음 페이지 여부")
    private boolean hasNext;

    @Builder
    private PagingResponse(long totalCount, int pageNumber, int totalPages, boolean hasNext) {
        this.totalCount = totalCount;
        this.pageNumber = pageNumber;
        this.totalPages = totalPages;
        this.hasNext = hasNext;
    }

    public static PagingResponse of(long totalCount, int pageNumber, int totalPages, boolean hasNext) {
        return PagingResponse.builder()
                .totalCount(totalCount)
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .hasNext(hasNext)
                .build();
    }

}
