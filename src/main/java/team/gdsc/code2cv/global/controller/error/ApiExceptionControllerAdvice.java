package team.gdsc.code2cv.global.controller.error;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.gdsc.code2cv.core.exception.ResourceNotFoundException;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionControllerAdvice {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalArgumentExceptionHandler(IllegalArgumentException e){
        log.debug("IllegalArgumentException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code("IllegalArgumentException")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        return ErrorResponse.builder()
                .debugMessage("MethodArgumentNotValidException")
                .code(fieldErrors.get(0).getField())
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        log.info("ResourceNotFoundException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code("ResourceNotFoundException")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse authenticationExceptionHandler(JwtException e){
        log.error("AuthenticationException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code("JWT")
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse runtimeExceptionHandler(RuntimeException e){
        log.error("RuntimeException : {}", e.getMessage());
        return ErrorResponse.builder()
                .debugMessage(e.getMessage())
                .code("RuntimeException")
                .build();
    }
}
