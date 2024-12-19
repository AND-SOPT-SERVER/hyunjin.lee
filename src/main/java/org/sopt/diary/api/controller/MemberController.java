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
            @RequestBody @Valid SignupRequest signupRequest
    ) {
        Long memberId = memberService.signupUser(signupRequest);
        return ResponseEntity.ok(new MemberResponse(memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        MemberResponse memberResponse = memberService.loginUser(loginRequest);
        return ResponseEntity.ok(memberResponse);
    }
}
