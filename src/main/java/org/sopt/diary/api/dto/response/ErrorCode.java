package org.sopt.diary.api.dto.response;

public enum ErrorCode {
    INVALID_INPUT_VALUE(40001, "유효하지 않은 요청입니다."),
    INVALID_INPUT_TITLE_DUPLICATE(40003, "이미 존재하는 제목입니다."),
    DIARY_NOT_FOUND(40004, "존재하지 않는 다이어리입니다."),
    USER_NOT_FOUND(40005, "존재하지 않는 사용자입니다."),
    USERNAME_ALREADY_EXISTS(40900, "이미 존재하는 아이디입니다."),
    NICKNAME_ALREADY_EXISTS(40901, "이미 존재하는 닉네임입니다."),
    INVALID_PASSWORD(40100, "비밀번호가 일치하지 않습니다."),
    INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }
}
