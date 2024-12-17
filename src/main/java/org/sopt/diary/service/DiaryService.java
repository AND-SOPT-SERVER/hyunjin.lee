package org.sopt.diary.service;

import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.GlobalException;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.domain.entity.DiaryEntity;
import org.sopt.diary.domain.entity.Category;
import org.sopt.diary.domain.entity.SoptMember;
import org.sopt.diary.domain.repository.DiaryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/*
서비스를 처리하는 클래스
 */
@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final MemberService memberService;

    public DiaryService(DiaryRepository diaryRepository, MemberService memberService) {
        this.diaryRepository = diaryRepository;
        this.memberService = memberService;
    }

    // 일기 작성
    public Long createDiary(String title, String content, Category category, Boolean isPublic, Long memberId) {
        // 존재하는 멤버인지 체크
        SoptMember soptMember = memberService.findMemberOrThrow(memberId);

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
                .collect(Collectors.toList());
    }

    // 특정 사용자의 일기 목록 조회
    public List<Diary> getMyDiaries(Long memberId, Category category) {
        SoptMember member = memberService.findMemberOrThrow(memberId);
        // 다이어리 목록을 조회하고 엔티티에서 도메인 모델로 변환
        List<DiaryEntity> diaryEntities;
        if(category == null){
            diaryEntities = diaryRepository.findTop10ByMemberOrderByCreatedAtDesc(member);
        } else{
            diaryEntities = diaryRepository.findTop10ByCategoryAndMemberOrderByCreatedAtDesc(category, member);
        }
        return diaryEntities.stream()
                .map(entity -> new Diary(entity.getId(), entity.getTitle()))
                .collect(Collectors.toList());
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
    public Diary updateDiary(Long diaryId, String title, String content, Long memberId) {
        // 다이어리 존재 여부 확인
        DiaryEntity diaryEntity = findDiaryOrThrow(diaryId);

        // 존재하는 멤버인지 체크
        memberService.findMemberOrThrow(memberId);

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
