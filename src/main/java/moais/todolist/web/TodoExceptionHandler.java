package moais.todolist.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.exception.TodoException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class TodoExceptionHandler {

    @ApiResponse(
            responseCode = "500", description = "알 수 없는 에러가 발생했습니다.",
            content = {@Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(type = "integer", example = "500")),
                    @SchemaProperty(name = "message", schema = @Schema(type = "string", example = "알 수 없는 에러가 발생했습니다."))})})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TodoException.class)
    public ApiCommonResponse<Object> errorResponse(TodoException exception) {
        log.error("ERROR: {}", exception.getMessage());
        return ApiCommonResponse.of(INTERNAL_SERVER_ERROR, exception.getMessage(), null);
    }

    @ApiResponse(
            responseCode = "500", description = "알 수 없는 에러가 발생했습니다.",
            content = {@Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(type = "integer", example = "500")),
                    @SchemaProperty(name = "message", schema = @Schema(type = "string", example = "알 수 없는 에러가 발생했습니다."))})})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiCommonResponse<Object> errorResponse(Exception exception) {
        log.error("ERROR: {}", exception.getMessage());
        return ApiCommonResponse.of(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.name(), null);
    }

}