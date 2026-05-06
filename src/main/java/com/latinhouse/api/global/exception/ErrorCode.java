package com.latinhouse.api.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    LESSON_NOT_FOUND("레슨을 찾을 수 없습니다", HttpStatus.NOT_FOUND),
    PROFILE_NOT_FOUND("프로필을 찾을 수 없습니다", HttpStatus.NOT_FOUND),

    NOT_AN_INSTRUCTOR("강사로 등록된 프로필이 아닙니다", HttpStatus.BAD_REQUEST),
    SEX_MISMATCH("강사의 성별이 일치하지 않습니다", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("잘못된 요청입니다", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED("", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}
