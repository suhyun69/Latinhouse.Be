package com.latinhouse.api.lesson.port.out;

import com.latinhouse.api.lesson.domain.Lesson;

public interface UpdateLessonPort {
    Lesson update(Lesson lesson);
}
