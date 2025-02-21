package com.example.exception;

import com.example.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, ErrorCode code) {
        log.error(ex.getMessage(), ex);
        ErrorResponse.Error error = new ErrorResponse.Error(
                code.name(), code.getMessage());
        return ResponseEntity.status(status).body(new ErrorResponse(error));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> alreadyExists(AlreadyExistsException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, errorCode);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> invalidCredentials(InvalidCredentialsException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, errorCode);
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<ErrorResponse> refreshTokenExpired(RefreshTokenException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return buildErrorResponse(ex, HttpStatus.UNAUTHORIZED, errorCode);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, errorCode);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> notValid(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        String errorMessage = String.join("; ", errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorResponse {

        private Error error;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Error {

            private String code;

            private String message;

        }
    }

}