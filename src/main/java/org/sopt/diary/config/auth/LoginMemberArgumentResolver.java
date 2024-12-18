package org.sopt.diary.config.auth;

import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.GlobalException;
import org.sopt.diary.domain.entity.SoptMember;
import org.sopt.diary.service.MemberService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

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

        Long memberId = Optional.ofNullable(memberIdHeader)
                .filter(id -> id.matches("\\d+"))
                .map(Long::valueOf)
                .orElseThrow(() -> new GlobalException(ErrorCode.INVALID_INPUT_VALUE));

        return memberService.findMemberOrThrow(memberId); // 서비스에서 사용자 조회
    }
}
