package com.latinhouse.api.lesson.adapter.in.web.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UpdateLessonWebRequest {

    @NotBlank
    private String name;

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

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;
}
