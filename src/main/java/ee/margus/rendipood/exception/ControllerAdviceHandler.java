package ee.margus.rendipood.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ControllerAdviceHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorMessage> handleException(RuntimeException ex){
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setStatus(400);
        errorMessage.setMessage(ex.getMessage());
        errorMessage.setDate(new Date());
        return ResponseEntity.status(400).body(errorMessage);
    }
}
