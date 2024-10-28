package org.sopt.diary.service;

import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.DiaryException;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.domain.entity.DiaryEntity;
import org.sopt.diary.domain.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        if (diaryRepository.existsByTitle(title)) {
            throw new DiaryException(ErrorCode.INVALID_INPUT_TITLE_DUPLICATE);
        }

        DiaryEntity savedDiary = diaryRepository.save(new DiaryEntity(title, content));
        return savedDiary.getId();
    }


    public List<Diary> getDiaries() {
        // 다이어리 목록을 조회하고 엔티티에서 도메인 모델로 변환
        return diaryRepository.findTop10ByOrderByCreatedAtDesc().stream()
                .map(entity -> new Diary(entity.getId(), entity.getTitle()))
                .collect(Collectors.toList());
    }

    public Diary getDiaryDetailById(Long id) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(id);
        return new Diary(
                diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(), diaryEntity.getCreatedAt(), diaryEntity.getUpdatedAt()
        );
    }

    public Diary updateDiary(Long id, String title, String content) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(id);

        // 제목, 내용, 수정 시간 갱신
        diaryEntity.updateDiary(title, content);

        // 수정 다이어리 반환~
        DiaryEntity updatedDiary = diaryRepository.save(diaryEntity);

        return new Diary(updatedDiary.getId(), updatedDiary.getTitle(), updatedDiary.getContent(), updatedDiary.getCreatedAt(), updatedDiary.getUpdatedAt());
    }

    public void deleteDiary(Long id) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(id);

        // 다이어리 삭제
        diaryRepository.delete(diaryEntity);
    }

    private DiaryEntity findDiaryOrThrow(final Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(
                        () -> new DiaryException(ErrorCode.INVALID_INPUT_VALUE)
                );

    }
}
