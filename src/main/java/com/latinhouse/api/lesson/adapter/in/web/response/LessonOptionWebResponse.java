package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.domain.Region;
import com.latinhouse.api.lesson.port.in.response.LessonOptionAppResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LessonOptionWebResponse {

    private final Long no;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> dateTimeSubTexts;
    private final Region region;
    private final String place;
    private final String placeUrl;
    private final String status;

    public LessonOptionWebResponse(LessonOptionAppResponse appResponse) {
        this.no = appResponse.getNo();
        this.startDateTime = appResponse.getStartDateTime();
        this.endDateTime = appResponse.getEndDateTime();
        this.dateTimeSubTexts = appResponse.getDateTimeSubTexts();
        this.region = appResponse.getRegion();
        this.place = appResponse.getPlace();
        this.placeUrl = appResponse.getPlaceUrl();
        this.status = appResponse.getStatus().name();
    }
}
