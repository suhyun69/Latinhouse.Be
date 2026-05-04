package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class LessonOption {

    private final Long no;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> dateTimeSubTexts;
    private final Region region;
    private final String place;
    private final String placeUrl;

    public LessonStatus status() {
        LocalDateTime now = LocalDateTime.now();
        if (startDateTime.isAfter(now)) {
            return LessonStatus.stand_by;
        } else if (!endDateTime.isBefore(now)) {
            return LessonStatus.in_progress;
        } else {
            return LessonStatus.done;
        }
    }
}
