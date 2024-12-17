package org.sopt.diary.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.sopt.diary.domain.entity.SoptMember;

public record SignupRequest(
        @NotBlank(message = "유저이름을 입력해주세요.")
        @Size(max = 10, message = "10자 이하의 이름으로 설정해주세요.")
        String username,

        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max = 10, message = "10자 이하의 닉네임으로 설정해주세요.")
        String nickname,

        @NotNull(message = "나이를 입력해주세요.")
        Integer age
) {
    public SoptMember toMember() {
        return new SoptMember(username, password, nickname, age);
    }
}
