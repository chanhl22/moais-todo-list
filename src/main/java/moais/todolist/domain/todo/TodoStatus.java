package moais.todolist.domain.todo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum TodoStatus {

    TODO("할 일"),
    IN_PROGRESS("진행중"),
    DONE("완료"),
    PENDING("대기");

    private final String description;

    @JsonCreator
    public static TodoStatus parsing(String inputValue) {
        return Stream.of(TodoStatus.values())
                .filter(category -> category.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }

}
