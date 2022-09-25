package uz.darico.exception.handler;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.darico.exception.exception.UniversalException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UniversalException.class)
    public ResponseEntity<?> InvalidExceptionHandler(UniversalException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }


}
