package org.sopt.diary.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SignupRequest {
    @NotBlank(message = "유저이름을 입력해주세요.")
    private final String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private final String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private final String nickname;

    @NotNull(message = "나이를 입력해주세요.")
    private final Integer age;

    public SignupRequest(String username, String password, String nickname, Integer age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public Integer getAge() { return age; }
}
