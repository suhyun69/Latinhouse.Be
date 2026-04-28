package com.latinhouse.api.lesson.domain;

public enum DiscountType {
    Earlybird("E"),
    Sex("S");

    private final String code;

    DiscountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DiscountType of(String code) {
        for (DiscountType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid discountType code: " + code);
    }
}