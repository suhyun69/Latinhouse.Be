package com.latinhouse.api.lesson.port.out;

import com.latinhouse.api.lesson.domain.Lesson;

import java.util.List;
import java.util.Optional;

public interface ReadLessonPort {
    Optional<Lesson> findByNo(Long no);
    List<Lesson> findAll();
}
