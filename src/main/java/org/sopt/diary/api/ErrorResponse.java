package org.sopt.diary.api;

/*
에러 응답을 정의한 클래스
 */
public class ErrorResponse {
    private final boolean success;
    private final Object data;
    private final ErrorDetail error;

    // ErrorCode enum 을 통해 에러 응답 생성
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
        INVALID_INPUT_VALUE(40000, "유효하지 않은 요청입니다."),
        INVALID_INPUT_TITLE_LENGTH(40001, "제목이 너무 길어요"),
        INVALID_INPUT_CONTENT_LENGTH(40002, "내용이 너무 길어요"),
        INVALID_INPUT_TITLE_DUPLICATE(40003, "이미 존재하는 제목입니다."),
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
