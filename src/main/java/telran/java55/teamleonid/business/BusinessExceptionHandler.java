package telran.java55.teamleonid.business;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(BusinessExceptionHandler.class);

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(EntityExistsException.class)
    private String handleConflict(HttpServletRequest request, Exception exception) {
        log.info(request.getRequestURI(), exception);
        return exception.toString();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    private String handleNotFound(HttpServletRequest request, Exception exception) {
        log.info(request.getRequestURI(), exception);
        return exception.toString();
    }
}
