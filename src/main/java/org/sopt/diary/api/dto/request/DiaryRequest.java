package org.sopt.diary.api.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.diary.domain.entity.Category;

public record DiaryRequest(
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 10, message = "제목은 10자를 초과할 수 없습니다.")
        String title,

        @NotBlank(message = "내용을 입력해주세요.")
        @Size(max = 30, message = "내용은 30자를 초과할 수 없습니다.")
        String content,

        @NotNull(message = "카테고리를 입력해주세요.")
        Category category,

        @NotNull(message = "공개여부를 선택해주세요.")
        Boolean isPublic
) {

}