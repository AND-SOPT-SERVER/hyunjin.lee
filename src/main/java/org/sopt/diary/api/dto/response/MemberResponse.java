package org.sopt.diary.api.dto.response;

import org.sopt.diary.domain.entity.SoptMember;

public record MemberResponse(Long memberId) {
    public MemberResponse(SoptMember member) {
        this(member.getId());
    }
}
