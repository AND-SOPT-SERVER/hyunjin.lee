package org.sopt.diary.config.auth;

import org.sopt.diary.domain.entity.SoptMember;
import org.sopt.diary.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberService memberService;

    public LoginMemberArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginMember.class)
                && parameter.getParameterType().equals(SoptMember.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        String memberIdHeader = webRequest.getHeader("Member-Id");
        if (memberIdHeader == null) {
            throw new IllegalArgumentException("Member-Id 헤더가 필요합니다.");
        }

        Long memberId = Long.valueOf(memberIdHeader);
        return memberService.findMemberOrThrow(memberId); // 서비스에서 사용자 조회
    }
}
