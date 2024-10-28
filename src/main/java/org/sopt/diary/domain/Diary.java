package org.sopt.diary.domain;

import java.time.LocalDateTime;

/*
Diary 관련 비즈니스 로직을 정의한 서비스 클래스
 */
public class Diary {
    private final long id;
    private final String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Diary(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Diary(long id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Diary(long id, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
