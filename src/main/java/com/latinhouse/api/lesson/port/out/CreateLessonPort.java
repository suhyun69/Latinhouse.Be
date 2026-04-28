package com.latinhouse.api.lesson.port.out;

import com.latinhouse.api.lesson.domain.Lesson;

public interface CreateLessonPort {
    Lesson create(Lesson lesson);
}
