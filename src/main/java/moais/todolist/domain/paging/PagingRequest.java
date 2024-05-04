package moais.todolist.domain.paging;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Getter
@NoArgsConstructor
public class PagingRequest {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 5;

    private final int page = DEFAULT_PAGE_NUMBER;

    private final int size = DEFAULT_PAGE_SIZE;

    public PageRequest makePageable() {
        return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDateTime"));
    }

}
