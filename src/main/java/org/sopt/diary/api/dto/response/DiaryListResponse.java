package org.sopt.diary.api.dto.response;

import org.sopt.diary.domain.Diary;

import java.util.List;
import java.util.stream.Collectors;

/*
List<Diary>를 받아 List<DiaryResponse>로 변환
 */
public record DiaryListResponse(List<DiaryResponse> diaries) {

    // Diary 리스트를 받아 DiaryResponse 리스트로 변환
    public static DiaryListResponse from(List<Diary> diaries) {
        List<DiaryResponse> diaryResponses = diaries.stream()
                .map(diary -> new DiaryResponse(diary.getId(), diary.getTitle()))
                .collect(Collectors.toList());
        return new DiaryListResponse(diaryResponses);
    }
}
