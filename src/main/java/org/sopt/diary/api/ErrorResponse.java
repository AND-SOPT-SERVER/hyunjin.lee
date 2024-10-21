package org.sopt.diary.api;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
에러 응답을 정의한 클래스
 */
public class ErrorResponse {
    @JsonProperty("success")
    private final boolean success;

    @JsonProperty("data")
    private final Object data;

    @JsonProperty("error")
    private final ErrorDetail error;

    // ErrorCode enum을 통해 에러 응답 생성
    public ErrorResponse(ErrorCode errorCode) {
        this.success = false;
        this.data = null;
        this.error = new ErrorDetail(errorCode.getCode(), errorCode.getMessage());
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public ErrorDetail getError() {
        return error;
    }

    // 내부 에러 디테일 클래스
    private record ErrorDetail(int code, String message) { }

    // ErrorCode enum을 통해 에러 메시지와 코드 관리
    public enum ErrorCode {
        INVALID_INPUT_VALUE(40001, "유효하지 않은 요청입니다."),
        INTERNAL_SERVER_ERROR(50000, "서버 내부 오류입니다.");

        private final int code;
        private final String message;

        ErrorCode(int code, String message) {
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
}
