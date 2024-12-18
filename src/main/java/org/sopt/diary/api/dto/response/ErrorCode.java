package org.sopt.diary.api.dto.response;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 400 Bad Request
    INVALID_INPUT_VALUE(40001, "유효하지 않은 요청입니다.", HttpStatus.BAD_REQUEST),
    INVALID_INPUT_CATEGORY_VALUE(40002, "카테고리는 필수 항목입니다.", HttpStatus.BAD_REQUEST),
    INVALID_INPUT_TITLE_DUPLICATE(40003, "이미 존재하는 제목입니다.", HttpStatus.BAD_REQUEST),

    // 401 Unauthorized
    INVALID_PASSWORD(40100, "비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),

    // 403 Forbidden3
    UNAUTHORIZED_ACCESS(40300, "권한이 없습니다.", HttpStatus.FORBIDDEN),

    // 404 Not Found
    DIARY_NOT_FOUND(40401, "존재하지 않는 다이어리입니다.", HttpStatus.NOT_FOUND),
    USER_NOT_FOUND(40402, "존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(40403, "존재하지 않는 카테고리입니다.", HttpStatus.NOT_FOUND),

    // 409 Conflict
    USERNAME_ALREADY_EXISTS(40901, "이미 존재하는 아이디입니다.", HttpStatus.CONFLICT),
    NICKNAME_ALREADY_EXISTS(40902, "이미 존재하는 닉네임입니다.", HttpStatus.CONFLICT),

    // 500 Internal Server Error
    INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
