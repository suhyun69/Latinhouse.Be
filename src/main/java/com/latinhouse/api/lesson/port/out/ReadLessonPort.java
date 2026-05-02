package com.latinhouse.api.lesson.port.out;

import com.latinhouse.api.lesson.domain.Lesson;
import com.latinhouse.api.lesson.port.in.request.FindLessonAppRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ReadLessonPort {
    Optional<Lesson> findByNo(Long no);
    List<Lesson> findAll();
    Page<Lesson> findAll(Pageable pageable, FindLessonAppRequest searchReq);
}
