package org.sopt.diary.api.dto.response;

public class ErrorDetail {
    private final int code;
    private final String message;

    public ErrorDetail(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
