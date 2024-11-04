package org.sopt.diary.domain.entity;

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

    @ManyToOne
    private SoptMember member;

    // 일기 제목
    @Column(nullable = false)
    public String title;

    // 일기 내용
    @Column(nullable = false)
    public String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Boolean isPublic;

    // 작성 날짜
    @Column
    public LocalDateTime createdAt;

    // 수정 날짜
    @Column
    public LocalDateTime updatedAt;

    // 생성자
    public DiaryEntity() { }

    public DiaryEntity(String title, String content, Category category, Boolean isPublic, SoptMember member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.isPublic = isPublic;
        this.member = member;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 다이어리 수정 메서드
    // 제목과 내용만 수정 가능
    // 수정 시간 자동 저장
    public void updateDiary(String title, String content) {
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getCategory() { return category.toString(); }
    public String getNickname() { return member.getNickname(); }

    // 카테고리 Enum
    public enum Category {
        FOOD, SCHOOL, STUDY, EXERCISE
    }
}
