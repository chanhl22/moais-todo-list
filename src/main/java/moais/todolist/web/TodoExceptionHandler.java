package moais.todolist.web;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import moais.todolist.domain.ApiCommonResponse;
import moais.todolist.domain.exception.StatusPolicyException;
import moais.todolist.domain.exception.TodoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@RestControllerAdvice
public class TodoExceptionHandler {

    @ApiResponse(
            responseCode = "200", description = "변경 조건에 적합하지 않아서 변경이 불가능합니다.",
            content = {@Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(type = "integer", example = "200")),
                    @SchemaProperty(name = "message", schema = @Schema(type = "string", example = "변경 조건에 적합하지 않아서 변경이 불가능합니다."))})})
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(StatusPolicyException.class)
    public ApiCommonResponse<Object> errorResponse(BindException exception) {
        log.info(exception.getMessage());
        return ApiCommonResponse.of(OK, exception.getMessage(), null);
    }

    @ApiResponse(
            responseCode = "500", description = "알 수 없는 에러가 발생했습니다.",
            content = {@Content(schemaProperties = {
                    @SchemaProperty(name = "code", schema = @Schema(type = "integer", example = "500")),
                    @SchemaProperty(name = "message", schema = @Schema(type = "string", example = "알 수 없는 에러가 발생했습니다."))})})
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TodoException.class)
    public ApiCommonResponse<Object> errorResponse(TodoException exception) {
        log.info("ERROR: {}", exception.getMessage());
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
