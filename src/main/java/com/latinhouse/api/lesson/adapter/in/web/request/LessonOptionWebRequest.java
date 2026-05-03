package com.latinhouse.api.lesson.adapter.in.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class LessonOptionWebRequest {

    private Long no;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "must match 'yyyy-MM-dd'")
    private String startDate;

    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "must match 'HH:mm'")
    private String startTime;

    @NotBlank
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "must match 'yyyy-MM-dd'")
    private String endDate;

    @NotBlank
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "must match 'HH:mm'")
    private String endTime;

    private List<String> dateTimeSubTexts;

    @NotBlank
    @Pattern(regexp = "^(GN|HD)$", message = "must be 'GN' or 'HD'")
    private String region;

    private String place;
    private String placeUrl;
}
