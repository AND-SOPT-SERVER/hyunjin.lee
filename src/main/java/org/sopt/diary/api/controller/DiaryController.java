package org.sopt.diary.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.sopt.diary.api.dto.request.DiaryRequest;
import org.sopt.diary.api.dto.response.DetailDiaryResponse;
import org.sopt.diary.api.dto.response.DiaryListResponse;
import org.sopt.diary.api.dto.response.DiaryResponse;
import org.sopt.diary.config.auth.LoginMember;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.domain.entity.Category;
import org.sopt.diary.domain.entity.SoptMember;
import org.sopt.diary.service.DiaryService;
import org.sopt.diary.util.CategoryConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
RESTful API를 정의하는 컨트롤러 클래스
클라이언트 요청을 처리하고, 서비스 계층을 호출하여 결과를 반환
*/
@Validated
@RestController
@RequestMapping("/luckybicky")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 일기 작성
    @PostMapping("/diaries")
    ResponseEntity<DiaryResponse> post(
            @LoginMember SoptMember member,
            @Valid @RequestBody DiaryRequest diaryRequest
    ) {
        // 다이어리 생성
        long diaryId = diaryService.createDiary(
                diaryRequest.title(),
                diaryRequest.content(),
                diaryRequest.category(),
                diaryRequest.isPublic(),
                member
        );
        return ResponseEntity.ok(new DiaryResponse(diaryId));
    }

    // 전체 일기 리스트 조회
    @GetMapping("/diaries")
    ResponseEntity<DiaryListResponse> getDiaryList(
            @RequestParam(value = "category", required = false) String categoryParam
    ) {
        Category category = CategoryConverter.convert(categoryParam);
        List<Diary> diaries = diaryService.getDiaries(category);
        return ResponseEntity.ok(DiaryListResponse.from(diaries));
    }

    // 특정 사용자의 일기 목록 조회
    @GetMapping("/diaries/me")
    ResponseEntity<DiaryListResponse> getMyDiaryList(
            @LoginMember SoptMember member,
            @RequestParam(value = "category", required = false) String categoryParam
    ) {
        Category category = CategoryConverter.convert(categoryParam);
        List<Diary> diaries = diaryService.getMyDiaries(member, category);
        return ResponseEntity.ok(DiaryListResponse.from(diaries));
    }

    // 일기 상세 조회
    @GetMapping("/diaries/{diaryId}")
    ResponseEntity<DetailDiaryResponse> getDiary(
            @PathVariable @Min(value = 1L, message = "다이어리 식별자는 양수로 이루어져야 합니다.") long diaryId
    ) {
        Diary diary = diaryService.getDiaryDetailById(diaryId);
        return ResponseEntity.ok(DetailDiaryResponse.from(diary));
    }

    // 일기 수정
    @PatchMapping("/diaries/{diaryId}")
    ResponseEntity<DetailDiaryResponse> updateDiary(
            @LoginMember SoptMember member,
            @PathVariable @Min(value = 1L, message = "다이어리 식별자는 양수로 이루어져야 합니다.") long diaryId,
            @Valid @RequestBody DiaryRequest diaryRequest)
    {
        Diary updateDiary = diaryService.updateDiary(
                diaryId,
                diaryRequest.title(),
                diaryRequest.content(),
                member
        );
        return ResponseEntity.ok(DetailDiaryResponse.from(updateDiary));
    }

    // 일기 삭제
    @DeleteMapping("/diaries/{diaryId}")
    ResponseEntity<DiaryResponse> deleteDiary(
            @PathVariable @Min(value = 1L, message = "다이어리 식별자는 양수로 이루어져야 합니다.") long diaryId
    ) {
        // 다이어리 삭제
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok().build();
    }
}

