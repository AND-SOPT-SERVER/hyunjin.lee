package org.sopt.diary.service;

import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*
서비스를 처리하는 클래스
 */
@Component
public class DiaryService {
    private static final int MAX_TITLE_LENGTH = 30;
    private static final int MAX_CONTENT_LENGTH = 100;
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Long createDiary(String title, String content) {
        // (1) title 의 길이가 30자를 넘으면 예외처리
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목이 30자 넘었지롱롱소세지빵~");
        }

        // (2) 내용의 길이가 100자 넘으면 예외처리
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new IllegalArgumentException("내용이 100자 넘었어용용죽겠지 ㅋㅋ");
        }

        // 일기 저장 후 ID 반환
        DiaryEntity savedDiary = diaryRepository.save(new DiaryEntity(title, content));
        return savedDiary.getId();
    }

    public List<Diary> getDiaries() {
        // (1) repository 로부터 DiaryEntity 를 가져옴
        List<DiaryEntity> diaryEntityList = diaryRepository.findTop10ByOrderByCreatedAtDesc();

        // (2) DiaryEntity 를 Diary 로 바꿔주는 작업
        final List<Diary> diaries = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntityList) {
            diaries.add(
                    new Diary(diaryEntity.getId(), diaryEntity.getTitle())
            );
        }

        return diaries;
    }

    public Diary getDiaryDetailById(Long id) {
        DiaryEntity diaryEntity = diaryRepository.findById(id).orElse(null);
        return diaryEntity != null
                ? new Diary(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(), diaryEntity.getCreatedAt())
                : null;
    }

    public Diary updateDiary(Long id, String title, String content) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 다이어리입니다."));

        // 제목, 내용 수정
        diaryEntity.setTitle(title);
        diaryEntity.setContent(content);

        // 수정 시간 갱신
        diaryEntity.setUpdatedAt(LocalDateTime.now());

        // 수정 다이어리 반환~
        DiaryEntity updatedDiary = diaryRepository.save(diaryEntity);

        return new Diary(updatedDiary.getId(), updatedDiary.getTitle(), updatedDiary.getContent(), updatedDiary.getCreatedAt(), updatedDiary.getUpdatedAt());
    }

    public void deleteDiary(Long id) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없는 다이어리를 어떻게 지우냐? ㅋㅋ"));

        // 다이어리 삭제
        diaryRepository.delete(diaryEntity);
    }
}
