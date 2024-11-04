package org.sopt.diary.domain.entity;

import jakarta.persistence.*;

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

    public SoptMember(String username, String password, String nickname, Integer age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getNickname() { return nickname; }
    public Integer getAge() { return age; }
}
