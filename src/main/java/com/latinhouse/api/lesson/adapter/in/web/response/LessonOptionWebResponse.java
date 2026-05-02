package com.latinhouse.api.lesson.adapter.in.web.response;

import com.latinhouse.api.lesson.domain.Region;
import com.latinhouse.api.lesson.port.in.response.LessonOptionAppResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LessonOptionWebResponse {

    private final String id;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> dateTimeSubTexts;
    private final Region region;
    private final String place;
    private final String placeUrl;

    public LessonOptionWebResponse(LessonOptionAppResponse appResponse) {
        this.id = appResponse.getId();
        this.startDateTime = appResponse.getStartDateTime();
        this.endDateTime = appResponse.getEndDateTime();
        this.dateTimeSubTexts = appResponse.getDateTimeSubTexts();
        this.region = appResponse.getRegion();
        this.place = appResponse.getPlace();
        this.placeUrl = appResponse.getPlaceUrl();
    }
}
