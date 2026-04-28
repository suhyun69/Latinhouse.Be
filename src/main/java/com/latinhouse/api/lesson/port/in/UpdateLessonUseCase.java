package com.latinhouse.api.lesson.port.in;

import com.latinhouse.api.lesson.port.in.request.UpdateLessonAppRequest;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;

public interface UpdateLessonUseCase {
    LessonAppResponse update(Long no, UpdateLessonAppRequest appReq);
}
