package org.sopt.diary.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.diary.domain.entity.DiaryEntity;

import java.time.LocalDateTime;

/*
다이어리 목록 조회 시 필요한 정보(id, title, createdAt, nickname)를 제공하는 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 포함하지 않음
public class DiaryResponse {
    private final long id;
    private String title;
    private LocalDateTime createdAt;
    private String nickname;

    // id만 반환하는 생성자
    public DiaryResponse(long id) {
        this.id = id;
    }

    // id와 title 을 모두 반환하는 생성자
    public DiaryResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    // id와 title, createdAt, nickname 을 모두 반환하는 생성자
    public DiaryResponse(long id, String title, LocalDateTime createdAt, String nickname) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.nickname = nickname;
    }

    public static DiaryResponse from(DiaryEntity diaryEntity) {
        return new DiaryResponse(
                diaryEntity.getId(),
                diaryEntity.getTitle(),
                diaryEntity.getCreatedAt(),
                diaryEntity.getNickname()
        );
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getNickname() { return nickname; }
}
