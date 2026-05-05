package com.latinhouse.api.profile.port.in.request;

import com.latinhouse.api.profile.domain.Sex;
import lombok.Builder;
import lombok.Value;

/**
 * findAll 검색 조건 AppRequest.
 * 모든 필드는 optional — null이면 해당 조건을 무시한다.
 */
@Value
@Builder
public class FindProfileAppRequest {

    String nickname;
    Sex sex;
    Boolean isInstructor;

    /**
     * Query parameter 문자열을 AppRequest로 변환한다.
     * sex 값이 유효하지 않으면 IllegalArgumentException을 던진다.
     * (GlobalExceptionHandler가 400으로 처리)
     */
    public static FindProfileAppRequest of(
            String nickname,
            String sex,
            Boolean isInstructor) {

        String parsedNickname = (nickname != null && !nickname.isBlank()) ? nickname.trim() : null;
        Sex parsedSex = (sex != null && !sex.isBlank()) ? Sex.of(sex) : null;

        return FindProfileAppRequest.builder()
                .nickname(parsedNickname)
                .sex(parsedSex)
                .isInstructor(isInstructor)
                .build();
    }
}
