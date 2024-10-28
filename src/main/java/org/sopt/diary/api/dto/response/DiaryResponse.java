package org.sopt.diary.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.diary.domain.entity.DiaryEntity;

/*
다이어리 목록 조회 시 필요한 정보(id, title)를 제공하는 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 포함하지 않음
public class DiaryResponse {
    private final long id;
    private String title;

    // id만 반환하는 생성자
    public DiaryResponse(long id) {
        this.id = id;
    }

    // id와 title 을 모두 반환하는 생성자
    public DiaryResponse(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public static DiaryResponse from(DiaryEntity diaryEntity) {
        return new DiaryResponse(
                diaryEntity.getId(),
                diaryEntity.getTitle()
        );
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
