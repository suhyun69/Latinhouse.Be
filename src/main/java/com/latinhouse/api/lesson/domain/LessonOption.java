package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class LessonOption {

    private final String id;             // 8-char alphanumeric, generated in Service
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final List<String> dateTimeSubTexts;
    private final Region region;
    private final String place;
    private final String placeUrl;
}
