package org.sopt.diary.api;

import java.util.List;

public class DiaryListResponse {
    private final List<DiaryResponse> diaries;

    public DiaryListResponse(List<DiaryResponse> diaries) {
        this.diaries = diaries;
    }

    public List<DiaryResponse> getDiaries() {
        return diaries;
    }
}
