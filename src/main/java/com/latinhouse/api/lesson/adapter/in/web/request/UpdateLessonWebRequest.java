package com.latinhouse.api.lesson.adapter.in.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateLessonWebRequest {

    @NotBlank
    private String title;

    @NotBlank
    @Pattern(regexp = "^(S|B)$", message = "must be 'S' or 'B'")
    private String genre;

    @NotBlank
    @Pattern(regexp = "^(GN|HD)$", message = "must be 'GN' or 'HD'")
    private String region;

    private String instructorLo;
    private String instructorLa;

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

    private String place;
    private String placeUrl;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    private BigDecimal maxDiscountAmount;
    private List<String> discountSubTexts;

    private String bank;
    private String accountNumber;
    private String accountOwner;

    @Valid
    private List<Discount> discounts;

    @Valid
    private List<Contact> contacts;

    @Getter
    @NoArgsConstructor
    public static class Discount {

        @NotBlank
        @Pattern(regexp = "^(E|S)$", message = "must be 'E' or 'S'")
        private String type;

        private String condition;

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        private BigDecimal amount;
    }

    @Getter
    @NoArgsConstructor
    public static class Contact {

        @NotBlank
        @Pattern(regexp = "^(P|K|I|Y|W)$", message = "must be 'P', 'K', 'I', 'Y', or 'W'")
        private String type;

        private String name;

        @NotBlank
        private String address;
    }
}
