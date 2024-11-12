package in.bushansirgur.restapi.exceptions;

import in.bushansirgur.restapi.io.ErrorObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Global Exception Handler for all the exceptions
 * @author Anand Raj S
 * **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorObject handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request)
    {
        log.error("Throwing the ResourceNotFoundException from Global Exception Handler {}",ex.getMessage());
        return ErrorObject.builder()
                    .errorCode("DATA_NOT_FOUND")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message(ex.getMessage())
                    .timestamp(new Date())
                    .build();
    }
}