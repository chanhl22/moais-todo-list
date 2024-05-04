package moais.todolist.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.OK;

@Getter
public class ApiCommonResponse<T> {

    private final int code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    public ApiCommonResponse(HttpStatus status, String message, T data) {
        this.code = status.value();
        this.message = message;
        this.data = data;
    }

    public static <T> ApiCommonResponse<T> of(HttpStatus httpStatus, String message, T data) {
        return new ApiCommonResponse<>(httpStatus, message, data);
    }

    public static <T> ApiCommonResponse<T> ok(T data) {
        return of(OK, OK.name(), data);
    }

}
