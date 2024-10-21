package org.sopt.diary.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 포함하지 않음
public class DiaryResponse {
    private final long id;
    private String title;
    private String content;
    private String createdAt;

    // id만 반환하는 생성자
    public DiaryResponse(long id) {
        this.id = id;
    }

    // id와 title 을 모두 반환하는 생성자
    public DiaryResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    // id, title, content, createdAt 을 모두 반환하는 생성자
    public DiaryResponse(long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        this.createdAt = createdAt.format(formatter);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
