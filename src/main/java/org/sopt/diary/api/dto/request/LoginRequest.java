package org.sopt.diary.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "유저이름을 입력해주세요.") String username,
        @NotBlank(message = "비밀번호를 입력해주세요.") String password
){

}