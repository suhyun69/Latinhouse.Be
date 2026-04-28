package com.latinhouse.api.lesson.port.in;

import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;

import java.util.List;

public interface FindLessonUseCase {
    LessonAppResponse findByNo(Long no);
    List<LessonAppResponse> findAll();
}
