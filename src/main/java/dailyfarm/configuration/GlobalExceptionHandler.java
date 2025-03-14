package dailyfarm.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j @RestControllerAdvice
public class GlobalExceptionHandler {

    // @ExceptionHandler(AccessDeniedException.class)
    // @ResponseStatus(HttpStatus.FORBIDDEN)
    // public void handleAccessDeniedException(
    //     AccessDeniedException exception
    // ) {
    //     log.error(exception.getMessage());
    // }

    // @ExceptionHandler(AuthenticationException.class)
    // @ResponseStatus(HttpStatus.UNAUTHORIZED)
    // public void handleAuthenticationException(
    //     AuthenticationException exception
    // ) {
    //     log.error(exception.getMessage());
    // }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleDataIntegrityViolationException(
        DataIntegrityViolationException exception
    ) {

    }

    // @ExceptionHandler(Exception.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // public void handleException(
    //     Exception exception
    // ) {
    //     log.error(exception.getMessage());
    // }
}
