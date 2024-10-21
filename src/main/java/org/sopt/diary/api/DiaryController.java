package org.sopt.diary.api;

import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
RESTful API를 정의하는 컨트롤러 클래스
클라이언트 요청을 처리하고, 서비스 계층을 호출하여 결과를 반환
*/
@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/luckybicky/diaries")
    ResponseEntity<?> post(@RequestBody DiaryRequest diaryRequest) {
        try {
            // 다이어리 생성
            long diaryId = diaryService.createDiary(diaryRequest.getTitle(), diaryRequest.getContent());
            return ResponseEntity.ok(new DiaryResponse(diaryId));

        } catch (IllegalArgumentException e) {
            // 유효하지 않은 입력에 대한 에러 응답 반환
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(ErrorResponse.ErrorCode.INVALID_INPUT_VALUE));

        } catch (RuntimeException e) {
            // 서버 내부 오류에 대한 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(ErrorResponse.ErrorCode.INTERNAL_SERVER_ERROR));
        }
    }


    @GetMapping("/luckybicky/diaries")
    ResponseEntity<DiaryListResponse> getDiaryList(){
        List<Diary> diaries = diaryService.getDiaries();

        // (2) Client 와 협약한 interface 로 변환
        List<DiaryResponse> diaryResponseList = new ArrayList<>();
        for (Diary diary : diaries) {
            diaryResponseList.add(new DiaryResponse(diary.getId(), diary.getTitle()));
        }

        return ResponseEntity.ok(new DiaryListResponse(diaryResponseList));
    }

    @GetMapping("/luckybicky/diaries/{diaryId}")
    ResponseEntity<DiaryResponse> getDiary(@PathVariable long diaryId) {
        Diary diary = diaryService.getDiaryDetailById(diaryId);
        if(diary == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(new DiaryResponse(diary.getId(), diary.getTitle(), diary.getContent(), diary.getCreatedAt()));
    }


}
