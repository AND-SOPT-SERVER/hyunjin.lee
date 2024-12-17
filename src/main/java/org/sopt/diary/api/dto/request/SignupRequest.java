package org.sopt.diary.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(
        @NotBlank(message = "유저이름을 입력해주세요.") String username,
        @NotBlank(message = "비밀번호를 입력해주세요.") String password,
        @NotBlank(message = "닉네임을 입력해주세요.") String nickname,
        @NotNull(message = "나이를 입력해주세요.") Integer age
) {

}
