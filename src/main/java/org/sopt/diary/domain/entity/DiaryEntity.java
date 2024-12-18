package org.sopt.diary.domain.entity;

import jakarta.persistence.*;
import org.sopt.diary.config.domain.BaseEntity;

/*
Diary 엔티티 클래스
데이터베이스 테이블과 매핑된 객체
 */
@Entity
public class DiaryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private SoptMember member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private Boolean isPublic;

    // 생성자
    protected DiaryEntity() { }

    public DiaryEntity(String title, String content, Category category, Boolean isPublic, SoptMember member) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.isPublic = isPublic;
        this.member = member;
    }

    // 다이어리 수정 메서드
    public void updateDiary(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getCategory() { return category.toString(); }
    public String getNickname() { return member.getNickname(); }
}
