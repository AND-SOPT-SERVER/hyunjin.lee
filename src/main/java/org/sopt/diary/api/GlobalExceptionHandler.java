package org.sopt.diary.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
전역에서 발생하는 예외를 처리하는 클래스
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // IllegalArgumentException 발생 시 처리
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_INPUT_VALUE));
    }

    // @RequestBody 바인딩 에러 (유효하지 않은 입력)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_INPUT_VALUE));
    }

    // 기타 예외 처리 (상태 코드 500)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_SERVER_ERROR));
    }
}
