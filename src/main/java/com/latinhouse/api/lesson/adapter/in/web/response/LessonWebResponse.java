package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.port.in.response.LessonAppResponse;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class LessonWebResponse {

    private final Long no;
    private final String title;
    private final Genre genre;
    private final String instructorLo;
    private final String instructorLa;
    private final List<LessonOptionWebResponse> options;
    private final BigDecimal price;

    public LessonWebResponse(LessonAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.title = appResponse.getTitle();
        this.genre = appResponse.getGenre();
        this.instructorLo = appResponse.getInstructorLo();
        this.instructorLa = appResponse.getInstructorLa();
        this.options = appResponse.getOptions() == null ? List.of() :
                appResponse.getOptions().stream().map(LessonOptionWebResponse::new).toList();
        this.price = appResponse.getPrice();
    }
}
