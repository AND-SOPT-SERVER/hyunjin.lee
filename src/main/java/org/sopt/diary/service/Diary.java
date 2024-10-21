package org.sopt.diary.service;

/*
Diary 관련 비즈니스 로직을 정의한 서비스 클래스
 */
public class Diary {
    private final long id;
    private final String name;

    public Diary(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
