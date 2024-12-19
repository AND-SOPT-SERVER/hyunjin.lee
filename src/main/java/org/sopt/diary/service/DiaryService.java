package org.sopt.diary.service;

import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.GlobalException;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.domain.entity.Category;
import org.sopt.diary.domain.entity.DiaryEntity;
import org.sopt.diary.domain.entity.SoptMember;
import org.sopt.diary.domain.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/*
서비스를 처리하는 클래스
 */
@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    // 일기 작성
    public Long createDiary(String title, String content, Category category, Boolean isPublic, SoptMember soptMember) {
        // 제목 중복체크
        if (diaryRepository.existsByTitle(title)) {
            throw new GlobalException(ErrorCode.INVALID_INPUT_TITLE_DUPLICATE);
        }

        DiaryEntity savedDiary = diaryRepository.save(
                new DiaryEntity(title, content, category, isPublic, soptMember)
        );
        return savedDiary.getId();
    }

    // 일기 전체 조회
    public List<Diary> getDiaries(Category category) {
        // 다이어리 목록을 조회하고 엔티티에서 도메인 모델로 변환
        List<DiaryEntity> diaryEntities;
        if(category == null){
            diaryEntities = diaryRepository.findTop10ByIsPublicTrueOrderByCreatedAtDesc();
        } else{
            diaryEntities = diaryRepository.findTop10ByCategoryAndIsPublicTrueOrderByCreatedAtDesc(category);
        }
        return diaryEntities.stream()
                .map(entity -> new Diary(entity.getId(), entity.getTitle(), entity.getCreatedAt(), entity.getNickname()))
                .toList();
    }

    // 특정 사용자의 일기 목록 조회
    public List<Diary> getMyDiaries(SoptMember soptMember, Category category) {
        // 다이어리 목록을 조회하고 엔티티에서 도메인 모델로 변환
        List<DiaryEntity> diaryEntities;
        if(category == null){
            diaryEntities = diaryRepository.findTop10ByMemberOrderByCreatedAtDesc(soptMember);
        } else{
            diaryEntities = diaryRepository.findTop10ByCategoryAndMemberOrderByCreatedAtDesc(category, soptMember);
        }
        return diaryEntities.stream()
                .map(entity -> new Diary(entity.getId(), entity.getTitle()))
                .toList();
    }

    // 일기 상세 조회
    public Diary getDiaryDetailById(Long id) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(id);
        return new Diary(
                diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(),
                diaryEntity.getCreatedAt(), diaryEntity.getUpdatedAt(),
                diaryEntity.getCategory(), diaryEntity.getNickname()
        );
    }

    // 일기 수정
    public Diary updateDiary(Long diaryId, String title, String content, SoptMember soptMember) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(diaryId);

        // 작성자 확인
        if (!diaryEntity.getNickname().equals(soptMember.getNickname())) {
            throw new GlobalException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 제목, 내용, 수정 시간 갱신
        diaryEntity.updateDiary(title, content);

        // 수정 다이어리 반환~
        DiaryEntity updatedDiary = diaryRepository.save(diaryEntity);

        return new Diary(updatedDiary.getId(), updatedDiary.getTitle(), updatedDiary.getContent(), updatedDiary.getCreatedAt(), updatedDiary.getUpdatedAt());
    }

    // 일기 삭제
    public void deleteDiary(Long id) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(id);

        // 다이어리 삭제
        diaryRepository.delete(diaryEntity);
    }

    // 다이어리 ID로 일기 조회
    private DiaryEntity findDiaryOrThrow(final Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(
                () -> new GlobalException(ErrorCode.INVALID_INPUT_VALUE)
        );
    }
}
