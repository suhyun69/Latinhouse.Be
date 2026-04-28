package com.latinhouse.api.lesson.domain;

public enum Region {
    Gangnam("GN"),
    Hongdae("HD");

    private final String code;

    Region(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Region of(String code) {
        for (Region region : values()) {
            if (region.code.equalsIgnoreCase(code)) {
                return region;
            }
        }
        throw new IllegalArgumentException("Invalid region code: " + code);
    }
}