package org.sopt.diary.api;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // null인 필드는 포함하지 않음
public class DiaryResponse {
    private final long id;
    private String name;

    // id만 반환하는 생성자
    public DiaryResponse(long id) {
        this.id = id;
    }

    // id와 name을 모두 반환하는 생성자
    public DiaryResponse(long id, String name) {
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
