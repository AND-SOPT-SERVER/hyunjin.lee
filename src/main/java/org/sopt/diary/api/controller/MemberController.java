package org.sopt.diary.api.controller;

import jakarta.validation.Valid;
import org.sopt.diary.api.dto.request.LoginRequest;
import org.sopt.diary.api.dto.request.SignupRequest;
import org.sopt.diary.api.dto.response.MemberResponse;
import org.sopt.diary.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/luckybicky")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberResponse> signup(
            @Valid @RequestBody SignupRequest signupRequest
    ) {
        Long memberId = memberService.signupUser(signupRequest.username(), signupRequest.password(), signupRequest.nickname(), signupRequest.age());
        return ResponseEntity.ok(new MemberResponse(memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        Long memberId = memberService.loginUser(loginRequest.username(), loginRequest.password());
        return ResponseEntity.ok(new MemberResponse(memberId));
    }
}
