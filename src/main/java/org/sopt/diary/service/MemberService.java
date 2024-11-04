package org.sopt.diary.service;

import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.DiaryException;
import org.sopt.diary.domain.entity.SoptMember;
import org.sopt.diary.domain.repository.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long signupUser(String username, String password, String nickname, Integer age) {
        if (memberRepository.existsByUsername(username)) {
            throw new DiaryException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (memberRepository.existsByNickname(nickname)) {
            throw new DiaryException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        SoptMember newUser = new SoptMember(username, password, nickname, age);
        return memberRepository.save(newUser).getId();
    }

    // 로그인
    public Long loginUser(String username, String password) {
        SoptMember user = memberRepository.findByUsername(username)
                .orElseThrow(() -> new DiaryException(ErrorCode.USER_NOT_FOUND));

        if (!user.getPassword().equals(password)) {
            throw new DiaryException(ErrorCode.INVALID_PASSWORD);
        }

        return user.getId();
    }
}
