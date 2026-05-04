package com.latinhouse.api.profile.domain;

public enum Sex {
    M, F;

    public static Sex of(String code) {
        return switch (code) {
            case "M" -> M;
            case "F" -> F;
            default -> throw new IllegalArgumentException("Invalid sex code: " + code);
        };
    }
}
