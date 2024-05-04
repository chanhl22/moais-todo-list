package moais.todolist.domain.paging;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class PagingRequest {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 5;

    private int page;

    private int size;

    @Builder
    private PagingRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public static PagingRequest of(Integer page, Integer size) {
        return PagingRequest.builder()
                .page(Optional.ofNullable(page).orElse(DEFAULT_PAGE_NUMBER))
                .size(Optional.ofNullable(size).orElse(DEFAULT_PAGE_SIZE))
                .build();
    }

    public PageRequest makePageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateTime"));
    }

}
