package com.latinhouse.api.lesson.domain;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Discount {
    private final Long id;        // Optional - 저장된 discount의 고유 ID
    private final DiscountType type;      // "E" (Earlybird), "S" (Sex별 할인) 등
    private final String condition; // 예: "3일 전 등록 시"
    private final BigDecimal amount;
}