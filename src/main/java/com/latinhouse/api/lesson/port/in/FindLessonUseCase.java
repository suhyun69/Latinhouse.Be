package com.latinhouse.api.lesson.port.in;

import com.latinhouse.api.lesson.port.in.request.FindLessonAppRequest;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import com.latinhouse.api.lesson.port.in.response.PagedLessonAppResponse;

import java.util.List;

public interface FindLessonUseCase {
    LessonAppResponse findByNo(Long no);
    List<LessonAppResponse> findAll();
    PagedLessonAppResponse findAll(int page, int size, FindLessonAppRequest searchReq);
}
