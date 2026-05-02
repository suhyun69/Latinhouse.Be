package com.latinhouse.api.lesson.domain;

public enum LessonStatus {
    /**
     * stand_by: startDateTime > now (아직 시작 전)
     */
    stand_by,

    /**
     * in_progress: startDateTime <= now <= endDateTime (진행 중)
     */
    in_progress,

    /**
     * done: endDateTime < now (이미 종료)
     */
    done;

    public static LessonStatus of(String value) {
        if (value == null) return null;
        for (LessonStatus status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value + ". Must be one of: stand_by, in_progress, done");
    }
}
