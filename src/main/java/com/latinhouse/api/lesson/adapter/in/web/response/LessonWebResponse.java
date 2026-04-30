package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.Region;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LessonWebResponse {

    private final Long no;
    private final String title;
    private final Genre genre;
    private final Region region;
    private final String instructorLo;
    private final String instructorLa;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final BigDecimal price;

    public LessonWebResponse(LessonAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.title = appResponse.getTitle();
        this.genre = appResponse.getGenre();
        this.region = appResponse.getRegion();
        this.instructorLo = appResponse.getInstructorLo();
        this.instructorLa = appResponse.getInstructorLa();
        this.startDateTime = appResponse.getStartDateTime();
        this.endDateTime = appResponse.getEndDateTime();
        this.price = appResponse.getPrice();
    }
}
