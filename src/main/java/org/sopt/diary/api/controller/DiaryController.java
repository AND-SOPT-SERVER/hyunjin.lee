package org.sopt.diary.api.controller;

import jakarta.validation.constraints.Min;
import org.sopt.diary.api.dto.request.DiaryRequest;
import org.sopt.diary.api.dto.response.DetailDiaryResponse;
import org.sopt.diary.api.dto.response.DiaryListResponse;
import org.sopt.diary.api.dto.response.DiaryResponse;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
RESTful API를 정의하는 컨트롤러 클래스
클라이언트 요청을 처리하고, 서비스 계층을 호출하여 결과를 반환
*/
@RestController
@RequestMapping("/luckybicky")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diaries")
    ResponseEntity<DiaryResponse> post(@Validated @RequestBody DiaryRequest diaryRequest) {
        // 다이어리 생성
        long diaryId = diaryService.createDiary(diaryRequest.getTitle(), diaryRequest.getContent());
        return ResponseEntity.ok(new DiaryResponse(diaryId));
    }

    @GetMapping("/diaries")
    ResponseEntity<DiaryListResponse> getDiaryList() {
        List<Diary> diaries = diaryService.getDiaries();
        return ResponseEntity.ok(DiaryListResponse.from(diaries));
    }

    @GetMapping("/diaries/{diaryId}")
    ResponseEntity<DetailDiaryResponse> getDiary(@PathVariable long diaryId) {
        Diary diary = diaryService.getDiaryDetailById(diaryId);
        return ResponseEntity.ok(DetailDiaryResponse.from(diary));
    }

    @PatchMapping("/diaries/{diaryId}")
    ResponseEntity<DetailDiaryResponse> updateDiary(
            @PathVariable @Min(value = 1L, message = "다이어리 식별자는 양수로 이루어져야 합니다.") long diaryId,
            @Validated @RequestBody DiaryRequest diaryRequest)
    {
        Diary updateDiary = diaryService.updateDiary(diaryId, diaryRequest.getTitle(), diaryRequest.getContent());
        return ResponseEntity.ok(DetailDiaryResponse.from(updateDiary));
    }

    @DeleteMapping("/diaries/{diaryId}")
    ResponseEntity<DiaryResponse> deleteDiary(
            @PathVariable @Min(value = 1L, message = "다이어리 식별자는 양수로 이루어져야 합니다.") long diaryId
    ) {
        // 다이어리 삭제
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.ok().build();
    }
}

