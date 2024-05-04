package moais.todolist.domain.todo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TodoStatus {

    TODO("할 일"),
    PROGRESS("진행중"),
    COMPLETED("완료"),
    HOLD("대기");

    private final String description;

}
