package com.latinhouse.api.lesson.port.in.request;

import com.latinhouse.api.lesson.adapter.in.web.request.CreateLessonWebRequest;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Value
@Builder
public class CreateLessonAppRequest {

    String name;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    BigDecimal price;

    public static CreateLessonAppRequest from(CreateLessonWebRequest webReq) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime startDateTime;
        LocalDateTime endDateTime;
        try {
            startDateTime = LocalDateTime.parse(webReq.getStartDate() + " " + webReq.getStartTime(), formatter);
            endDateTime = LocalDateTime.parse(webReq.getEndDate() + " " + webReq.getEndTime(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date/time format.");
        }

        return CreateLessonAppRequest.builder()
                .name(webReq.getName())
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .price(webReq.getPrice())
                .build();
    }
}
