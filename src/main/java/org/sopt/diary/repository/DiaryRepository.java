package org.sopt.diary.repository;

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
}
