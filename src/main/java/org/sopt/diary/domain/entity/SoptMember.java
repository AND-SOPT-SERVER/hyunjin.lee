package org.sopt.diary.domain.entity;

import jakarta.persistence.*;
import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.GlobalException;

@Entity
public class SoptMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer age;

    public SoptMember() {}

    public SoptMember(Long id, String username, String password, String nickname, Integer age) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    public SoptMember(String username, String password, String nickname, Integer age) {
        this(null, username, password, nickname, age);
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getNickname() { return nickname; }
    public Integer getAge() { return age; }

    public void verifyPassword(String password) {
        if (!password.equals(this.password)) {
            throw new GlobalException(ErrorCode.INVALID_PASSWORD);
        }
    }
}
