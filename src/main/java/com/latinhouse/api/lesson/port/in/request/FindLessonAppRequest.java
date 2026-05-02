package com.latinhouse.api.lesson.port.in.request;

import com.latinhouse.api.lesson.domain.Genre;
import com.latinhouse.api.lesson.domain.LessonStatus;
import com.latinhouse.api.lesson.domain.Region;
import lombok.Builder;
import lombok.Value;

/**
 * findAll 검색 조건 AppRequest.
 * 모든 필드는 optional — null이면 해당 조건을 무시한다.
 */
@Value
@Builder
public class FindLessonAppRequest {

    Genre genre;
    Region region;
    String instructor;
    LessonStatus status;

    /**
     * Query parameter 문자열을 AppRequest로 변환한다.
     * 유효하지 않은 enum 값이 전달되면 IllegalArgumentException을 던진다.
     * (GlobalExceptionHandler가 400으로 처리)
     */
    public static FindLessonAppRequest of(
            String genre,
            String region,
            String instructor,
            String status) {

        Genre parsedGenre = (genre != null && !genre.isBlank()) ? Genre.of(genre) : null;
        Region parsedRegion = (region != null && !region.isBlank()) ? Region.of(region) : null;
        String parsedInstructor = (instructor != null && !instructor.isBlank()) ? instructor.trim() : null;
        LessonStatus parsedStatus = (status != null && !status.isBlank()) ? LessonStatus.of(status) : null;

        return FindLessonAppRequest.builder()
                .genre(parsedGenre)
                .region(parsedRegion)
                .instructor(parsedInstructor)
                .status(parsedStatus)
                .build();
    }
}
