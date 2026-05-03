package com.latinhouse.api.lesson.port.in.response;

import com.latinhouse.api.lesson.domain.LessonOption;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class LessonOptionAppResponse {

    private final Long no;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> dateTimeSubTexts;
    private final Region region;
    private final String place;
    private final String placeUrl;

    public LessonOptionAppResponse(LessonOption option) {
        this.no = option.getNo();
        this.startDateTime = option.getStartDateTime();
        this.endDateTime = option.getEndDateTime();
        this.dateTimeSubTexts = option.getDateTimeSubTexts();
        this.region = option.getRegion();
        this.place = option.getPlace();
        this.placeUrl = option.getPlaceUrl();
    }
}
