package org.sopt.diary.service;

import org.sopt.diary.api.dto.request.LoginRequest;
import org.sopt.diary.api.dto.request.SignupRequest;
import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.dto.response.MemberResponse;
import org.sopt.diary.api.exception.GlobalException;
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
    public long signupUser(SignupRequest signupRequest) {
        if (memberRepository.existsByUsername(signupRequest.username())) {
            throw new GlobalException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (memberRepository.existsByNickname(signupRequest.nickname())) {
            throw new GlobalException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        SoptMember newUser = memberRepository.save(signupRequest.toMember());
        return newUser.getId();
    }

    // 로그인
    public MemberResponse loginUser(LoginRequest loginRequest) {
        SoptMember member = memberRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));

        member.verifyPassword(loginRequest.password());
        return new MemberResponse(member);
    }

    // Member ID로 멤버 조회
    public SoptMember findMemberOrThrow(final Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new GlobalException(ErrorCode.USER_NOT_FOUND)
        );
    }
}
