package com.latinhouse.api.lesson.port.in.request;

import com.latinhouse.api.lesson.adapter.in.web.request.LessonOptionWebRequest;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Value
@Builder
public class LessonOptionAppRequest {

    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    List<String> dateTimeSubTexts;
    Region region;
    String place;
    String placeUrl;

    public static LessonOptionAppRequest from(LessonOptionWebRequest webReq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = LocalDateTime.parse(webReq.getStartDate() + " " + webReq.getStartTime(), formatter);
            endDateTime = LocalDateTime.parse(webReq.getEndDate() + " " + webReq.getEndTime(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format.");
        }
        if (!startDateTime.isBefore(endDateTime)) {
            throw new IllegalArgumentException("startDateTime must be before endDateTime.");
        }

        return LessonOptionAppRequest.builder()
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .dateTimeSubTexts(webReq.getDateTimeSubTexts())
                .region(Region.of(webReq.getRegion()))
                .place(webReq.getPlace())
                .placeUrl(webReq.getPlaceUrl())
                .build();
    }
}
