package org.sopt.diary.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.sopt.diary.api.dto.response.ErrorCode;
import org.sopt.diary.api.exception.GlobalException;
import java.util.Arrays;

public enum Category {
    FOOD, SCHOOL, STUDY, EXERCISE;

    @JsonCreator
    public static Category from(String value) {
        if (value == null) return null;
        return Arrays.stream(values())
                .filter(category -> category.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new GlobalException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @JsonValue
    public String getValue() {
        return this.name();
    }
}
