package org.sopt.diary.repository;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/*
Diary 엔티티 클래스
데이터베이스 테이블과 매핑된 객체
 */
@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    // 일기 제목
    @Column(nullable = false)
    public String title;

    // 일기 내용
    @Column(nullable = false)
    public String content;

    // 작성 날짜
    @Column
    public LocalDateTime createdAt;

    // 수정 날짜
    @Column
    public LocalDateTime updatedAt;

    // 생성자
    public DiaryEntity() { }

    public DiaryEntity(final String title) {
        this.title = title;
    }

    public DiaryEntity(String title, String content) {
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
