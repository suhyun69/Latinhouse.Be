package com.latinhouse.api.lesson.port.in;

import com.latinhouse.api.lesson.port.in.request.CreateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;

public interface CreateLessonUseCase {
    LessonAppResponse create(CreateLessonAppRequest appReq);
}
