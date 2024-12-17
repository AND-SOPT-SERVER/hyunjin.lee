package org.sopt.diary.domain.repository;

import org.sopt.diary.domain.entity.DiaryEntity;
import org.sopt.diary.domain.entity.Category;
import org.sopt.diary.domain.entity.SoptMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/*
데이터베이스 CRUD 작업을 처리하는 Repository 인터페이스
 */
@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
    // 최근 등록된 순서로 10개의 일기만 가져오는 메서드
    List<DiaryEntity> findTop10ByOrderByCreatedAtDesc();

    // 최근 등록된 순서로 공개된 일기 10개만 가져오는 메서드
    List<DiaryEntity> findTop10ByIsPublicTrueOrderByCreatedAtDesc();

    // 최근 등록된 순서로 내가 작성한 일기 10개만 가져오는 메서드
    List<DiaryEntity> findTop10ByMemberOrderByCreatedAtDesc(SoptMember member);

    // 최근 등록된 순서로 공개된 일기 카테고리별 10개만 가져오는 메서드
    List<DiaryEntity> findTop10ByCategoryAndIsPublicTrueOrderByCreatedAtDesc(Category category);

    // 최근 등록된 순서로 내가 작성한 일기 카테고리별 10개만 가져오는 메서드
    List<DiaryEntity> findTop10ByCategoryAndMemberOrderByCreatedAtDesc(Category category, SoptMember member);

    // 해당 제목을 가진 일기가 존재하는지 판단하는 메서드
    boolean existsByTitle(String title);
}
