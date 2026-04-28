package com.latinhouse.api.lesson.domain;

public enum ContactType {
    Phone("P"),
    Kakaotalk("K"),
    Instagram("I"),
    Youtube("Y"),
    Web("W")
    ;

    private final String code;

    ContactType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ContactType of(String code) {
        for (ContactType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid contactType code: " + code);
    }
}
