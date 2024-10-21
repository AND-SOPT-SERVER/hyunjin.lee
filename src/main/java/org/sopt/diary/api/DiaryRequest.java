package org.sopt.diary.api;

public class DiaryRequest {
    private final String diaryId;
    private final String title;
    private final String content;

    public DiaryRequest(String diaryId, String title, String content) {
        this.diaryId = diaryId;
        this.title = title;
        this.content = content;
    }

    public String getDiaryId() {
        return diaryId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
