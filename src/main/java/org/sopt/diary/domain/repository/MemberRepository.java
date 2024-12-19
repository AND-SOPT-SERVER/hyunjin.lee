package org.sopt.diary.domain.repository;

import org.sopt.diary.domain.entity.SoptMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface MemberRepository extends JpaRepository<SoptMember, Long> {
    Optional<SoptMember> findByUsername(String username);
    boolean existsByUsername(String username);

    Optional<SoptMember> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
}