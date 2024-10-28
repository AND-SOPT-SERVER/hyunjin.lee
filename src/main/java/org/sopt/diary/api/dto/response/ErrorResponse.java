package org.sopt.diary.api.dto.response;

/*
에러 응답을 정의한 클래스
 */
public class ErrorResponse {
    private final boolean success;
    private final Object data;
    private final ErrorDetail error;

    // 기본 메시지 생성
    public ErrorResponse(ErrorCode errorCode) {
        this.success = false;
        this.data = null;
        this.error = new ErrorDetail(errorCode.getCode(), errorCode.getMessage());
    }

    // 커스텀 메시지 생성
    public ErrorResponse(ErrorCode errorCode, String customMessage) {
        this.success = false;
        this.data = null;
        this.error = new ErrorDetail(errorCode.getCode(),
                customMessage != null ? customMessage : errorCode.getMessage());
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
}
