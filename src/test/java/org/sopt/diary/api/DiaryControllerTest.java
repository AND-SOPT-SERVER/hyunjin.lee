package org.sopt.diary.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sopt.diary.domain.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiaryService diaryService;

    // 다이어리 생성 성공 테스트
    @Test
    void testCreateDiary_Success() throws Exception {
        // given
        String title = "Test Diary Title";
        String content = "Test Diary Content";
        long diaryId = 1L;

        // diaryService.createDiary 호출 시 diaryId 반환하도록 설정
        given(diaryService.createDiary(Mockito.anyString(), Mockito.anyString())).willReturn(diaryId);

        // when & then
        mockMvc.perform(post("/luckybicky/diaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + title + "\", \"content\": \"" + content + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(diaryId));
    }

    // 다이어리 생성 실패 테스트 (유효하지 않은 입력)
    @Test
    void testCreateDiary_InvalidInput_ShouldReturn400() throws Exception {
        // given
        String invalidTitle = ""; // 유효하지 않은 제목

        // diaryService.createDiary 호출 시 IllegalArgumentException 던지도록 설정
        doThrow(new IllegalArgumentException("유효하지 않은 입력")).when(diaryService).createDiary(Mockito.anyString(), Mockito.anyString());

        // when & then
        mockMvc.perform(post("/luckybicky/diaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + invalidTitle + "\", \"content\": \"Test Content\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value(40001))
                .andExpect(jsonPath("$.error.message").value("유효하지 않은 요청입니다."));
    }

    // 다이어리 생성 시 서버 내부 오류가 발생할 때 테스트
    @Test
    void testCreateDiary_InternalServerError_ShouldReturn50000() throws Exception {
        // given
        String title = "Test Title";
        String content = "Test Content";

        // diaryService.createDiary 호출 시 RuntimeException 발생하도록 설정
        doThrow(new RuntimeException("서버 내부 오류")).when(diaryService).createDiary(Mockito.anyString(), Mockito.anyString());

        // when & then
        mockMvc.perform(post("/luckybicky/diaries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"" + title + "\", \"content\": \"" + content + "\"}"))
                .andExpect(status().isInternalServerError()) // 500 상태 코드 확인
                .andExpect(jsonPath("$.error.code").value(50000)) // 50000번 에러 코드 확인
                .andExpect(jsonPath("$.error.message").value("서버 내부 오류입니다.")); // 에러 메시지 확인
    }

    // 다이어리 목록 조회 성공 테스트
    @Test
    void testGetDiaryList_Success() throws Exception {
        // given
        Diary diary1 = new Diary(1L, "Diary 1");
        Diary diary2 = new Diary(2L, "Diary 2");

        // diaryService.getDiaries 호출 시 다이어리 목록 반환하도록 설정
        given(diaryService.getDiaries()).willReturn(List.of(diary1, diary2));

        // when & then
        mockMvc.perform(get("/luckybicky/diaries")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.diaries[0].id").value(diary1.getId()))
                .andExpect(jsonPath("$.diaries[0].title").value(diary1.getTitle()))
                .andExpect(jsonPath("$.diaries[1].id").value(diary2.getId()))
                .andExpect(jsonPath("$.diaries[1].title").value(diary2.getTitle()));
    }

    @Test
    void testGetDiary_Success() throws Exception {
        // given
        long diaryId = 1L;
        Diary diary = new Diary(diaryId, "Test Diary Title", "Test Diary Content", LocalDateTime.now());

        // diaryService.getDiaryDetailById 호출 시 다이어리 반환하도록 설정
        given(diaryService.getDiaryDetailById(diaryId)).willReturn(diary);

        // when & then
        mockMvc.perform(get("/luckybicky/diaries/{diaryId}", diaryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(diary.getId()))
                .andExpect(jsonPath("$.title").value(diary.getTitle()))
                .andExpect(jsonPath("$.content").value(diary.getContent()))
                .andExpect(jsonPath("$.createdAt").value(diary.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyyMMdd"))));
    }

    // 다이어리 상세 조회 시 다이어리를 찾을 수 없을 때 테스트
    @Test
    void testGetDiary_NotFound() throws Exception {
        // given
        long diaryId = 1L;

        // diaryService.getDiaryDetailById 호출 시 null 반환하도록 설정
        given(diaryService.getDiaryDetailById(diaryId)).willReturn(null);

        // when & then
        mockMvc.perform(get("/luckybicky/diaries/{diaryId}", diaryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // 404 상태 코드 확인
    }
}
