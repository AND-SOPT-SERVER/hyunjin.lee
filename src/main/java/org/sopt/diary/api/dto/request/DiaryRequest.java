package org.sopt.diary.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DiaryRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 30, message = "제목은 30자를 초과할 수 없습니다.")
    private final String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 100, message = "내용은 100자를 초과할 수 없습니다.")
    private final String content;

    public DiaryRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
