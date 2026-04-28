package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LessonWebResponse {

    private final Long no;
    private final String name;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final BigDecimal price;

    public LessonWebResponse(LessonAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.name = appResponse.getName();
        this.startDateTime = appResponse.getStartDateTime();
        this.endDateTime = appResponse.getEndDateTime();
        this.price = appResponse.getPrice();
    }
}
