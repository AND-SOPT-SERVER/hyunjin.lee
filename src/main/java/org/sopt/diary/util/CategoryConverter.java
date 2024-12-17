package org.sopt.diary.util;

import org.sopt.diary.domain.entity.Category;

public class CategoryConverter {

    public static Category convert(String categoryParam) {
        return Category.from(categoryParam);
    }
}
