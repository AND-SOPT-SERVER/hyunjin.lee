package org.sopt.diary.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.diary.domain.Diary;

import java.time.format.DateTimeFormatter;

/*
다이어리 상세 조회 시 필요한 모든 정보(id, title, content, createdAt, updatedAt)를 제공하는 DTO
 */
@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 포함하지 않음
public record DetailDiaryResponse(
        long id, String title, String content, String createdAt, String updatedAt, String category, String nickname
) {

    public static DetailDiaryResponse from(Diary diary) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:ss");
        return new DetailDiaryResponse(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getCreatedAt().format(formatter),
                diary.getUpdatedAt() != null ? diary.getUpdatedAt().format(formatter) : null,
                diary.getCategory(),
                diary.getNickname()
        );
    }
}
