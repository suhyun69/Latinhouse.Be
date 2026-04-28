package com.latinhouse.api.lesson.port.in.response;

import com.latinhouse.api.lesson.domain.Lesson;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LessonAppResponse {

    private final Long no;
    private final String name;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final BigDecimal price;

    public LessonAppResponse(Lesson lesson) {
        this.no = lesson.getNo();
        this.name = lesson.getName();
        this.startDateTime = lesson.getStartDateTime();
        this.endDateTime = lesson.getEndDateTime();
        this.price = lesson.getPrice();
    }
}
