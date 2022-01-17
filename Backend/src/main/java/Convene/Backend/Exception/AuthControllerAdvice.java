package Convene.Backend.Exception;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
//@Slf4j
public class AuthControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AuthExceptions.UserExistsException.class})
    public ResponseEntity<Object> handleUserExistsException(AuthExceptions.UserExistsException exception, WebRequest webRequest){
        LocalDate timeStamp = LocalDate.now();
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({AuthExceptions.EmailValidationRecordNotFoundException.class})
    public ResponseEntity<Object> handleEmailVerificationException(AuthExceptions.EmailValidationRecordNotFoundException exception, WebRequest webRequest){
        LocalDate timeStamp = LocalDate.now();
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({AuthExceptions.InvalidVerificationCodeException.class})
    public ResponseEntity<Object> handleInvalidCode(AuthExceptions.InvalidVerificationCodeException exception, WebRequest webRequest) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.CONFLICT
        );
    }


    @ExceptionHandler({AuthExceptions.AccountLockedException.class})
    public ResponseEntity<String> handleLockedAccountException(AuthExceptions.AccountLockedException exception, WebRequest request) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.FORBIDDEN
        );
    }


    @ExceptionHandler({AuthExceptions.InvalidAuthCredentialsException.class})
    public ResponseEntity<String> handleInvalidAUthCredentials(AuthExceptions.InvalidAuthCredentialsException exception, WebRequest request) {
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }
}
